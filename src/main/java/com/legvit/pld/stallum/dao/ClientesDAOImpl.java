package com.legvit.pld.stallum.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.vo.ListasAll;
import com.legvit.pld.stallum.vo.MbConsultaVO;
import com.legvit.pld.vo.ClienteRelConsultaVO;

public class ClientesDAOImpl extends SimpleQueryJdbcDaoSupport implements ClientesDAO {

	public List<String> obtenSociedadesMercantiles() {
		String query = getQueries().getProperty("obten.sociedades.mercantiles");
        List<String> data = getJdbcTemplate().query(query, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("PREFIJO_SOC_MERC");
			}
		});
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ListasAll> findByNameAll(String name) throws PldException {
		getJdbcTemplate().setResultsMapCaseInsensitive(true);
		SimpleJdbcCall procListas = new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName("spGetCleanList_temp_test_V")
                .returningResultSet("listas", new RowMapper<ListasAll>() {

					@Override
					public ListasAll mapRow(ResultSet rs, int rowNum) throws SQLException {
						ListasAll listaResult = new ListasAll();
						listaResult.setFolio(rs.getString("TXT_FOLIO"));
		                listaResult.setNombreOriginal(rs.getString("TXT_NOMBRE_ORIGINAL"));
		                listaResult.setNombre(rs.getString("TXT_NOMBRE_COMPLETO"));
		                listaResult.setTipo(rs.getInt("INT_LISTA"));
						return listaResult;
					}
				});
		
		Map<String, Object> m = procListas.execute(new Object[] { name });
        return (List<ListasAll>) m.get("listas");
    }
	
	
}
