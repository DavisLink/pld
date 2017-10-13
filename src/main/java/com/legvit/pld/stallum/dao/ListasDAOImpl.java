package com.legvit.pld.stallum.dao;

import java.lang.annotation.Inherited;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.stallum.vo.ListasAll;

public class ListasDAOImpl extends SimpleQueryJdbcDaoSupport implements ListasDAO {

	@Override
	public List<ListasAll> findByNameAll(String name) throws PldException {
		getJdbcTemplate().setResultsMapCaseInsensitive(true);
		SimpleJdbcCall procListas = new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName("spGetCleanList_temp_test_V")
                .returningResultSet("listas", new BeanPropertyRowMapper<ListasAll>(ListasAll.class));
		
		Map<String, Object> m = procListas.execute(new Object[] { name });
        return (List<ListasAll>) m.get("listas");
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ClientesCRM> obtenClientesCRM() {
		String query = getQueries().getProperty("obten.clientes.crm");
		List<ClientesCRM> listado = getJdbcTemplate().query(query, new RowMapper<ClientesCRM>() {
			public ClientesCRM mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClientesCRM aux = new ClientesCRM();
				aux.setIdCRM(rs.getInt("ID_CRM"));
				aux.setIdUnicoCliente(rs.getInt("ID_UNICO"));
				aux.setNombres(rs.getString("NOMBRE"));
				aux.setApellidoPaterno(rs.getString("APPATERNO"));
				aux.setApellidoMaterno(rs.getString("APMATERNO"));
				aux.setTipoCliente(rs.getString("TIPOCLIENTE"));
				aux.setTipoPersona(rs.getString("TIPOPERSONA"));
				aux.setTipoRazonSocial(rs.getString("TIPORAZONSOCIAL"));
				return aux;
			}
		});
		return listado;
	}
}
