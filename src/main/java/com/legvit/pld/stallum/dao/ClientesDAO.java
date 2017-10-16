package com.legvit.pld.stallum.dao;

import java.util.List;

import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.vo.ListasAll;
import com.legvit.pld.stallum.vo.MbConsultaVO;
import com.legvit.pld.vo.ClienteRelConsultaVO;

public interface ClientesDAO {

	List<String> obtenSociedadesMercantiles();

	List<ListasAll> findByNameAll(String name) throws PldException;
	
	/**
	 * Obtiene el registro de la tabla mb_consulta para aplicar las reglas de
	 * validaci&oacute;n.
	 * 
	 * @param idCRM
	 *            identificador del CRM
	 * @param idClienteUnico
	 *            identificador del cliente &uacute;nico.
	 * @return Value Object con los datos obtenidos de la b&uacute;squeda.
	 */
	MbConsultaVO obtenRegistroMBConsulta(String idCRM, String idClienteUnico);
	
	/**
	 * Realiza la inserci&oacute;n de un registro en la base de datos.
	 * 
	 * @param mbConsulta
	 *            Value Object que contiene los valores a insertarse.
	 * @return identificador del registro insertado.
	 */
	int insertaRegistro(MbConsultaVO mbConsulta);
	
	/**
	 * Obtiene el top 5 de los registros de la tabla.
	 * 
	 * @param idCRM
	 *            identificador del crm
	 * @param idClienteUnico
	 *            identificador del cliente
	 * @return listado con registros.
	 */
	List<MbConsultaVO> obtenTopMBConsulta(String idCRM, String idClienteUnico);

	/**
	 * Obtiene el listado de clientes para realizar el proceso de terceros
	 * relacionados.
	 * 
	 * @return listado con los clientes.
	 */
	List<MbConsultaVO> obtenRegistros();
	
	/**
	 * Realiza la insercion de un registro en la btala mb_consulta_cliente
	 * 
	 * @param clienteRelVO
	 *            value object con los datos a insertarse.
	 */
	void insertaRegistroClienteRel(ClienteRelConsultaVO clienteRelVO);
	
	/**
	 * Realiza la inserci&oacute;n del registro de beneficiario.
	 * 
	 * @param clienteRelVO
	 *            value object con los datos a insertarse.
	 */
	void insertaRegistroBenefRel(ClienteRelConsultaVO clienteRelVO);
}
