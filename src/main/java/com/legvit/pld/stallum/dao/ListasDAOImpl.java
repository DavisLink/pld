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
import com.legvit.pld.vo.RelacionadosVO;

public class ListasDAOImpl extends SimpleQueryJdbcDaoSupport implements ListasDAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ClientesCRM> obtenClientesCRM() {
		String query = getQueries().getProperty("obten.clientes.crm");
		List<ClientesCRM> listado = getJdbcTemplate().query(query, new RowMapper<ClientesCRM>() {
			public ClientesCRM mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClientesCRM aux = new ClientesCRM();
				aux.setIdCRM(rs.getString("idCRMCliente"));
				aux.setIdUnicoCliente(rs.getString("idUnicoCliente"));
				aux.setNombres(rs.getString("nombreRazonSocialCliente"));
				aux.setApellidoPaterno(rs.getString("apellidoPaternoCliente"));
				aux.setApellidoMaterno(rs.getString("apellidoMaternoCliente"));
				aux.setTipoCliente(rs.getString("idTipoCliente"));
				aux.setTipoPersona(rs.getString("idTipoPersonaCliente"));
				aux.setTipoRazonSocial(rs.getString("tipoRazonSocialCliente"));
				return aux;
			}
		});
		return listado;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertaClienteCRM(ClientesCRM clienteCRM) {
		String query = getQueries().getProperty("inserta.clientes.crm");
		getJdbcTemplate().update(query, new Object[]{clienteCRM.getIdCRM(), clienteCRM.getCalificacion(), clienteCRM.getIdConsulta(), clienteCRM.getFechaRegistro()});
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RelacionadosVO> obtenRelacionados(String idCRM){
		String query = getQueries().getProperty("obten.clientes.relacionados");
		List<RelacionadosVO> listado = getJdbcTemplate().query(query, new RowMapper<RelacionadosVO>() {

			@Override
			public RelacionadosVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RelacionadosVO relacionado = new RelacionadosVO();
				relacionado.setApeMaternoRelacionado(rs.getString("apellidoMaternoRelacionado"));
				relacionado.setApePaternoRelacionado(rs.getString("apellidoPaternoRelacionado"));
				relacionado.setIdCRMCliente(rs.getString("idCRMCliente"));
				relacionado.setIdCRMRelacionado(rs.getString("idCRMRelacionado"));
				relacionado.setNombreRazonSocialRelacionado(rs.getString("nombreRazonSocialRelacionado"));
				relacionado.setTipoPersonaRelacionado(rs.getString("tipoPersonaRelacionado"));
				relacionado.setTipoRazonSocialRelacionado(rs.getString("tipoRazonSocialRelacionado"));
				relacionado.setTipoRelacionConClienteRelacionado(rs.getString("tipoRelacionConClienteRelacionado"));
				return relacionado;
			}
		});
		return listado;
	}
}
