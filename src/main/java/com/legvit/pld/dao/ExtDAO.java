package com.legvit.pld.dao;

import com.legvit.pld.vo.ControlPLDVO;

/**
 * Contrato para las operaciones relacionadas a la base de datos de control.
 * 
 * @author aaviac
 * @version 1.0, 14102017
 */
public interface ExtDAO {

	/**
	 * Realiza la inserci&oacute;n del registro en la tabla de control.
	 * 
	 * @param controlPLDVO
	 *            Value Object que contiene los valores a insertarse en la tabla
	 *            de control.
	 */
	void insertaRegistro(ControlPLDVO controlPLDVO);
	
	/**
	 * Obtiene el registro de la tabla de control identificado por el
	 * par&aacute;metro recibido.
	 * 
	 * @param idCRM
	 *            identificador del Sistema CRM en el cual se basar&aacute;ra la
	 *            b&uacute;squeda.
	 * @return Value object con los datos encontrados.
	 */
	ControlPLDVO obtenRegistroControl(String idCRM);
	
	/**
	 * Obtiene el registro de la tabla de control mas reciente.
	 * 
	 * @param idCRM
	 *            identificador del crm del cliente
	 * @return Value object con los datos obtenidos en la consulta
	 */
	ControlPLDVO obtenRegistroControlReciente(String idCRM);
}
