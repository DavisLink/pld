package com.legvit.pld.stallum.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.vo.ListasAll;
import com.legvit.pld.stallum.vo.MbConsultaVO;

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
	
	RowMapper<MbConsultaVO> MbConsultaMapper = new RowMapper<MbConsultaVO>() {
		
		@Override
		public MbConsultaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			MbConsultaVO aux = new MbConsultaVO();
			aux.setIdMbConsulta(rs.getString("Id_mb_consulta"));
			aux.setIdClienteCRM(rs.getString("id_cliente_crm"));
			aux.setCalificacion(rs.getString("status_consulta"));
			aux.setPorcentajeConsulta(rs.getString("porcentaje_busqueda"));
			aux.setIdCliente(rs.getString("id_cliente"));
			return aux;
		}
	};
	
	public MbConsultaVO obtenRegistroMBConsulta(String idCRM, String idClienteUnico) {
		String query = getQueries().getProperty("");
		List<MbConsultaVO> listadoConsulta = getJdbcTemplate().query(query, MbConsultaMapper, new Object[]{idCRM, idClienteUnico});
		MbConsultaVO consultaVO = null;
		if (listadoConsulta != null && listadoConsulta.isEmpty()) {
			consultaVO = listadoConsulta.get(0);
		}
		return consultaVO;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertaRegistro(MbConsultaVO mbConsulta) {
		String query = getQueries().getProperty("inserta.registro.mbconsulta");
		getJdbcTemplate().update(query, new Object[]{mbConsulta.getIdClienteCRM(), new Date(),mbConsulta.getCalificacion(), mbConsulta.getCalificacion(), mbConsulta.getPorcentajeConsulta(), mbConsulta.getIdCliente()});
	}
}
