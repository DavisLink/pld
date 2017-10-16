package com.legvit.pld.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.legvit.pld.stallum.dao.SimpleQueryJdbcDaoSupport;
import com.legvit.pld.vo.ControlPLDVO;

/**
 * Clase que implementa el contrato definido en la interface <code>ExtDAO</code>
 * 
 * @author aaviac
 * @version 1.0, 14102017
 */
public class ExtDAOImpl extends SimpleQueryJdbcDaoSupport implements ExtDAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertaRegistro(ControlPLDVO controlPLDVO) {
		String query = getQueries().getProperty("inserta.registro.control");
		
		getJdbcTemplate().update(query, new Object[] { controlPLDVO.getIdCRM(), 
														controlPLDVO.getNombreCompleto(),
														new Date(), 
														controlPLDVO.getCalificacion() });

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
}
