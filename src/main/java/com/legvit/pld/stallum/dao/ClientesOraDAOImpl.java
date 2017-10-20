package com.legvit.pld.stallum.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.legvit.pld.stallum.vo.MbConsultaVO;
import com.legvit.pld.vo.ClienteRelConsultaVO;
import com.legvit.pld.vo.ControlPLDVO;
import com.legvit.pld.vo.HiloVO;

public class ClientesOraDAOImpl extends SimpleQueryJdbcDaoSupport implements ClientesOraDAO {

	
RowMapper<MbConsultaVO> MbConsultaMapper = new RowMapper<MbConsultaVO>() {
		
		@Override
		public MbConsultaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			MbConsultaVO aux = new MbConsultaVO();
			aux.setIdMbConsulta(rs.getInt("ID_MB_CONSULTA"));
			aux.setIdClienteCRM(rs.getString("ID_CLIENTE_CRM"));
			aux.setCalificacion(rs.getInt("STATUS_CONSULTA"));
			aux.setPorcentajeConsulta(rs.getInt("PORCENTAJE_BUSQUEDA"));
			aux.setIdCliente(rs.getString("ID_CLIENTE"));
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
				PreparedStatement ps = connection.prepareStatement(query, new String[] { "ID_MB_CONSULTA" });
				ps.setString(1, mbConsulta.getIdClienteCRM());
				ps.setDate(2, new java.sql.Date(mbConsulta.getFechaRegistro().getTime()));
				ps.setInt(3, mbConsulta.getCalificacion());
				ps.setString(4, mbConsulta.getDescripcion());
				ps.setInt(5, mbConsulta.getPorcentajeConsulta());
				ps.setString(6, mbConsulta.getIdCliente());
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
		String query = "SELECT * FROM mb_consulta_batch";
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertaRegistro(ControlPLDVO controlPLDVO) {
		String query = getQueries().getProperty("inserta.registro.control");
		
		getJdbcTemplate().update(query, new Object[] { controlPLDVO.getIdCRM(), 
														controlPLDVO.getNombreCompleto(),
														new Date(), 
														controlPLDVO.getCalificacion(),
														controlPLDVO.getNoCoincidencias()});

	}
	
	/**
	 * Mapper para los registros de la tabla de control.
	 */
	RowMapper<ControlPLDVO> RegistroControlCRM = new RowMapper<ControlPLDVO>() {

		@Override
		public ControlPLDVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ControlPLDVO controlVO = new ControlPLDVO();
			controlVO.setNombreCompleto(rs.getString("nombre_completo"));
			controlVO.setFechaRegistro(rs.getDate("fecha_registro"));
			controlVO.setCalificacion(rs.getInt("calificacion"));
			controlVO.setIdCRM(rs.getString("id_crm"));
			controlVO.setId(rs.getInt("id_registro"));
			return null;
		}
	};
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ControlPLDVO obtenRegistroControl(String idCRM) {
		String query = getQueries().getProperty("select.registro.control");
		List<ControlPLDVO> listadoControl = getJdbcTemplate().query(query, RegistroControlCRM, new Object[]{idCRM});
		ControlPLDVO aux = null;
		if (listadoControl != null && listadoControl.size() > 0) {
			aux = listadoControl.get(0);
		}
		return aux;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ControlPLDVO obtenRegistroControlReciente(String idCRM) {
		String query = getQueries().getProperty("select.registro.control.reciente");
		List<ControlPLDVO> listadoControl = getJdbcTemplate().query(query, RegistroControlCRM, new Object[]{idCRM});
		ControlPLDVO aux = null;
		if (listadoControl != null && !listadoControl.isEmpty()) {
			aux = listadoControl.get(0);
		}
		return aux;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertaHilo(HiloVO hiloVO) {
		String query = getQueries().getProperty("insert.registro.hilo");
		getJdbcTemplate().update(query, new Object[] { hiloVO.getRegistrosProcesados(), hiloVO.getEstatus(),
				hiloVO.getHoraInicio(), hiloVO.getFechaRegistro() });
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actualizaHilo(int estatus, String horaTermino, int idRegistro) {
		String query = getQueries().getProperty("insert.registro.hilo");
		getJdbcTemplate().update(query, new Object[] { estatus, horaTermino, idRegistro });
	}
}
