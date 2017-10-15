package com.legvit.pld.stallum.dao;

import java.util.List;

import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.vo.ListasAll;
import com.legvit.pld.stallum.vo.MbConsultaVO;

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
	
	void insertaRegistro(MbConsultaVO mbConsulta);
}
