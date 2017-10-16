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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MbConsultaVO obtenRegistroMBConsulta(String idCRM, String idClienteUnico) {
		String query = getQueries().getProperty("obten.registro.consulta");
		List<MbConsultaVO> listadoConsulta = getJdbcTemplate().query(query, MbConsultaMapper, new Object[]{idCRM, idClienteUnico});
		MbConsultaVO consultaVO = null;
		if (listadoConsulta != null && !listadoConsulta.isEmpty()) {
			consultaVO = listadoConsulta.get(0);
		}
		return consultaVO;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int insertaRegistro(final MbConsultaVO mbConsulta) {
		final String query = getQueries().getProperty("inserta.registro.mbconsulta");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query, new String[] { "Id_mb_consulta" });
				ps.setString(1, mbConsulta.getIdClienteCRM());
				ps.setDate(2, new java.sql.Date(mbConsulta.getFechaRegistro().getTime()));
				ps.setString(3, mbConsulta.getCalificacion());
				ps.setString(4, mbConsulta.getPorcentajeConsulta());
				ps.setString(5, mbConsulta.getIdCliente());
				return ps;
			}
		}, keyHolder);

		int key = ((BigDecimal) keyHolder.getKey()).intValue();
		return key;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MbConsultaVO> obtenTopMBConsulta(String idCRM, String idClienteUnico) {
		String query = getQueries().getProperty("obten.registro.consulta");
		List<MbConsultaVO> listadoConsulta = getJdbcTemplate().query(query, MbConsultaMapper, new Object[]{idCRM, idClienteUnico});
		return listadoConsulta;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MbConsultaVO> obtenRegistros() {
		String query = "SELECT * FROM mb_consulta";
		List<MbConsultaVO> listado = getJdbcTemplate().query(query, MbConsultaMapper);
		return listado;
	}

	@Override
	public void insertaRegistroClienteRel(ClienteRelConsultaVO clienteRelVO) {
		String query = getQueries().getProperty("inserta.registro.mbconsulta.cliente");
		getJdbcTemplate().update(query, new Object[]{clienteRelVO.getIdMbConsulta(), clienteRelVO.getNombreCliente(), clienteRelVO.getApPaternoCliente(), clienteRelVO.getApMaternoCliente()});
	}
	
	@Override
	public void insertaRegistroBenefRel(ClienteRelConsultaVO clienteRelVO) {
		String query = getQueries().getProperty("inserta.registro.mbconsulta.benef");
		getJdbcTemplate().update(query, new Object[]{clienteRelVO.getIdMbConsulta(), clienteRelVO.getNombreCliente(), clienteRelVO.getApPaternoCliente(), clienteRelVO.getApMaternoCliente()});
	}
}
