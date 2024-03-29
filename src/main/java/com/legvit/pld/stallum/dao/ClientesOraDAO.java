package com.legvit.pld.stallum.dao;

import java.util.List;

import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.stallum.vo.MbConsultaVO;
import com.legvit.pld.vo.ClienteRelConsultaVO;
import com.legvit.pld.vo.ControlPLDVO;
import com.legvit.pld.vo.HiloVO;

public interface ClientesOraDAO {

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
	
	/**
	 * Realiza la inserci&oacute;n del registro correspondiente al hilo en
	 * ejecuci&oacute;n
	 * 
	 * @param hiloVO
	 *            Value object que contiene los datos del hilo ejecutado.
	 */
	int insertaHilo(HiloVO hiloVO);
	
	/**
	 * Realiza la actualizaci&oacute;n del registro correspondiente al hilo en el
	 * registro.
	 * 
	 * @param estatus
	 *            estatus del hilo.
	 * @param horaTermino
	 *            hora de t&eacute;rmino de ejecuci&oacute;n del hilo.
	 * @param idRegistro
	 *            identificador del hilo en la base de datos.
	 */
	void actualizaHilo(int estatus, String horaTermino, int idRegistro);
	
	/**
	 * Inserta un registro en la tabla de r&eacute;plica para llevar el control
	 * acerca de los registros insertados por cada uno de los hilos en
	 * ejecuci&oacute;n
	 * 
	 * @param clienteCRM
	 *            value object con los valores que ser&aacute;n insertados en la
	 *            tabla de r&eacute;plica,
	 */
	void insertaReplicaRegistro(ClientesCRM clienteCRM) ;
	
	/**
	 * Obtiene los hilos que fallaron en la ejecuci&oacute;n
	 * 
	 * @param idEstatus
	 *            identificador del estatus del hilo.
	 * 
	 * @return Listado de hilos que tuvieron error en la ejecuci&oacute;n.
	 */
	List<HiloVO> obtenHilosErroneos(int idEstatus);
	
	/**
	 * Obtiene los registros correspondientes al hilo enviado como par&aacute;metro.
	 * 
	 * @return listado de clientes.
	 */
	List<ClientesCRM> obtenRegistrosPorHilo(int idHilo);
	
	/**
	 * Realiza la actualizaci&oacute;n del registro en la tabla de r&eacute;plica. 
	 * 
	 * @param idRegistro identificador del registro.
	 */
	void actualizaEstatusRegistroReplica(String idCRM, int estatus);
}
