package com.legvit.pld.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legvit.pld.dao.ExtDAO;
import com.legvit.pld.stallum.dao.ClientesDAO;
import com.legvit.pld.stallum.dao.ListasDAO;
import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.stallum.vo.MbConsultaVO;
import com.legvit.pld.vo.ControlPLDVO;
import com.mysql.jdbc.integration.jboss.MysqlValidConnectionChecker;

/**
 * Clase que cumple el contrato definido en la interface
 * <code>ReglasService</code>
 * 
 * @author aaviac
 * @version 1.0, 14102017
 */
@Service(value="reglasService")
public class ReglasServiceImpl implements ReglasService {

	@Autowired
	ClientesDAO clientesDAO;
	
	@Autowired
	ExtDAO pldExtDAO;
	
	@Autowired
	ListasDAO listasDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void primeraRegla(ClientesCRM clienteCRM, int noCoincidencias) {
		
		//SI EL REGISTRO DEL DIA ANTERIOR TIENE EL MISMO NUMERO DE COINCIDENCIA Y NO ESTÁ CALIFICADO (CALIFICACION 4 O 5)
		//GUARDA EL REGISTRO EN MDBConsultas Y SE INSERTA EN TABLA DEL CRM
		
		//1.- Obtener el registro de la tabla MB_Consulta (SQLServer) y se busca por id_cliente_unico de clientesxamb de mysql y el id_crm
		//status_consulta (calificacion 4 o 5)
		
		MbConsultaVO consultaVO = clientesDAO.obtenRegistroMBConsulta(clienteCRM.getIdCRM(), clienteCRM.getIdUnicoCliente());

		// no coincidencias es de nuestra tabla de control control_pld (mysql)
		//SE OBTIENE EL REGISTRO MAS RECIENTE DE NUESTRA TABLA DE CONTROL
		ControlPLDVO controlVO = pldExtDAO.obtenRegistroControlReciente(clienteCRM.getIdCRM());
		
		if (controlVO.getNoCoincidencias() == noCoincidencias) {
			if (Integer.parseInt(consultaVO.getCalificacion()) == 4 || Integer.parseInt(consultaVO.getCalificacion()) == 5) {
			
				//SE INSERTA EN MBCONSULTA
				MbConsultaVO mbConsulta = new MbConsultaVO();
				mbConsulta.setCalificacion(consultaVO.getCalificacion());
				mbConsulta.setIdClienteCRM(clienteCRM.getIdCRM());
				mbConsulta.setIdCliente(clienteCRM.getIdUnicoCliente());
				//TODO OBTENER PORCENTAJE DE CONSULTA
				mbConsulta.setPorcentajeConsulta("98");
				
				clientesDAO.insertaRegistro(mbConsulta);
				
				ClientesCRM aux = new ClientesCRM();
				aux.setIdCRM(clienteCRM.getIdCRM());
				aux.setIdConsulta("");
				aux.setFechaRegistro("");
				listasDAO.insertaClienteCRM(aux);
			} else if (Integer.parseInt(consultaVO.getCalificacion()) == 6 || Integer.parseInt(consultaVO.getCalificacion()) == 7) {
				ClientesCRM aux = new ClientesCRM();
				aux.setIdCRM(clienteCRM.getIdCRM());
				aux.setIdConsulta("");
				aux.setFechaRegistro("");
				listasDAO.insertaClienteCRM(aux);
			}
			/*//SE INSERTA EN TABLA CRM
				ControlPLDVO controlPLDVO = new ControlPLDVO();
				controlPLDVO.setIdCRM(clienteCRM.getIdCRM());
				controlPLDVO.setCalificacion(Integer.parseInt(consultaVO.getCalificacion()));
				controlPLDVO.setFechaRegistro(new Date());
				controlPLDVO.setNombreCompleto(controlVO.getNombreCompleto());
				
				pldExtDAO.insertaRegistro(controlPLDVO);*/
		}
		
		//se inserta en mb_consultas con los datos que tenemos al momento
		
		// insertamos en CRM ing_proceso_masivo
		
		
		
	}

}
