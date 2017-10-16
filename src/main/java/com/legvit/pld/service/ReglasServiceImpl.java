package com.legvit.pld.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legvit.pld.dao.ExtDAO;
import com.legvit.pld.stallum.dao.ClientesDAO;
import com.legvit.pld.stallum.dao.ListasDAO;
import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.stallum.vo.MbConsultaVO;
import com.legvit.pld.util.EnvioCorreo;
import com.legvit.pld.util.PorpertiesLoaderUtil;
import com.legvit.pld.vo.ControlPLDVO;

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
	public void validaReglas(ClientesCRM clienteCRM, int noCoincidencias, float porcentajeCoincidencia, int calificacion) {
		
		List<String> lines = new ArrayList<String>();
		String descripcionCal = null;
		String idCRM = clienteCRM.getIdCRM();
		String idUnico = clienteCRM.getIdUnicoCliente();
		
		if (idCRM.length() > 12 || idUnico.length() != 8) {
			lines.add(clienteCRM.getIdCRM() + "|" +clienteCRM.getNombres() + "|" + clienteCRM.getApellidoPaterno() + "|" + clienteCRM.getApellidoMaterno() + "|" + 3 + "|" + "ID de cliente fuera de rango.");
		}
		
		//SI EL REGISTRO DEL DIA ANTERIOR TIENE EL MISMO NUMERO DE COINCIDENCIA Y NO ESTÁ CALIFICADO (CALIFICACION 4 O 5)
		//GUARDA EL REGISTRO EN MDBConsultas Y SE INSERTA EN TABLA DEL CRM
		
		//1.- Obtener el registro de la tabla MB_Consulta (SQLServer) y se busca por id_cliente_unico de clientesxamb de mysql y el id_crm
		//status_consulta (calificacion 4 o 5)
		
		//ULTIMO REGISTRO
			MbConsultaVO consultaVO = clientesDAO.obtenRegistroMBConsulta(clienteCRM.getIdCRM(), clienteCRM.getIdUnicoCliente());
	
			// no coincidencias es de nuestra tabla de control control_pld (mysql)
			//SE OBTIENE EL REGISTRO MAS RECIENTE DE NUESTRA TABLA DE CONTROL
			ControlPLDVO controlVO = pldExtDAO.obtenRegistroControlReciente(clienteCRM.getIdCRM());
	
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			
			//SE INSERTA EN TABLA CONTROL PROPIA
			ControlPLDVO controlPLDVO = new ControlPLDVO();
			controlPLDVO.setIdCRM(clienteCRM.getIdCRM());
			controlPLDVO.setCalificacion(calificacion);
			controlPLDVO.setFechaRegistro(cal.getTime());
			controlPLDVO.setNombreCompleto(clienteCRM.getNombres() + " " + clienteCRM.getApellidoPaterno() + " " + clienteCRM.getApellidoMaterno());
			pldExtDAO.insertaRegistro(controlPLDVO);
			
			
			if (calificacion == 4) {
				descripcionCal = "ID Cliente no encontrado";
			} else if (calificacion == 5) {
				descripcionCal = "Registro actualizado correctamente";
			}
			lines.add(clienteCRM.getIdCRM() + "|" +clienteCRM.getNombres() + "|" + clienteCRM.getApellidoPaterno() + "|" + clienteCRM.getApellidoMaterno() + "|" + calificacion + "|" + descripcionCal);
			
			if (controlVO != null) {
				if (controlVO.getNoCoincidencias() == noCoincidencias) {
					
					
					//REGLA 1: SI EL REGISTRO TIENE EL MISMO NUMERO DE COINCIDENCIAS Y TIENE CALIFICACION 4 O 5
					//SE INSERTA EN CRM Y 
					if (Integer.parseInt(consultaVO.getCalificacion()) == 4 || Integer.parseInt(consultaVO.getCalificacion()) == 5) {
						
						//INSERCION EN MB_CONSULTA
						MbConsultaVO mbConsulta = new MbConsultaVO();
						mbConsulta.setCalificacion(consultaVO.getCalificacion());
						mbConsulta.setIdClienteCRM(clienteCRM.getIdCRM());
						mbConsulta.setIdCliente(clienteCRM.getIdUnicoCliente());
						mbConsulta.setFechaRegistro(cal.getTime());
						mbConsulta.setPorcentajeConsulta(String.valueOf(porcentajeCoincidencia));
						int idConsulta = clientesDAO.insertaRegistro(mbConsulta);
						
						//INSERTA EN ing_proceso_masivo_mdb
						ClientesCRM aux = new ClientesCRM();
						aux.setIdCRM(clienteCRM.getIdCRM());
						aux.setIdConsulta(String.valueOf(idConsulta));
						aux.setFechaRegistro(dateFormat.format(cal.getTime()));
						listasDAO.insertaClienteCRM(aux);
						
						
						
					//REGLA 2: SI EL REGISTRO TIENE EL MISMO NUMERO DE COINCIDENCIAS  Y CALIFICACION 6 O 7
					//SOLO SE INSERTA EN MB_CONSULTA
					} else if (Integer.parseInt(consultaVO.getCalificacion()) == 6 || Integer.parseInt(consultaVO.getCalificacion()) == 7) {
						
						//SE INSERTA EN MB_CONSULTA
						MbConsultaVO mbConsulta = new MbConsultaVO();
						mbConsulta.setCalificacion(consultaVO.getCalificacion());
						mbConsulta.setIdClienteCRM(clienteCRM.getIdCRM());
						mbConsulta.setIdCliente(clienteCRM.getIdUnicoCliente());
						mbConsulta.setFechaRegistro(cal.getTime());
						mbConsulta.setPorcentajeConsulta(String.valueOf(porcentajeCoincidencia));
						clientesDAO.insertaRegistro(mbConsulta);
					} else if (Integer.parseInt(consultaVO.getCalificacion()) >= 0 && Integer.parseInt(consultaVO.getCalificacion()) <= 3) {
						//OBTENGO EL REGISTRO ANTERIOR
						List<MbConsultaVO> listado = clientesDAO.obtenTopMBConsulta(clienteCRM.getIdCRM(), clienteCRM.getIdUnicoCliente());
						if (listado.size() > 2) {
							MbConsultaVO aux = listado.get(1);
							
							if (Integer.parseInt(aux.getCalificacion()) == 6 || Integer.parseInt(aux.getCalificacion()) == 6) {
								MbConsultaVO mbConsulta = new MbConsultaVO();
								mbConsulta.setCalificacion(consultaVO.getCalificacion());
								mbConsulta.setIdClienteCRM(clienteCRM.getIdCRM());
								mbConsulta.setIdCliente(clienteCRM.getIdUnicoCliente());
								mbConsulta.setFechaRegistro(cal.getTime());
								mbConsulta.setPorcentajeConsulta(String.valueOf(porcentajeCoincidencia));
								int idConsulta = clientesDAO.insertaRegistro(mbConsulta);
							}	
						}
					}
					
				//REGLA 4:SI LAS COINCIDENCIAS SON DIFERENTES Y TIENE CALIFICACION 4 O 5
				//SE INSERTA EN MB_CONSULTA 
				//SE INSERTA EN CRM
				} else {
					//INSERCION EN MB_CONSULTA
					MbConsultaVO mbConsulta = new MbConsultaVO();
					mbConsulta.setCalificacion(consultaVO.getCalificacion());
					mbConsulta.setIdClienteCRM(clienteCRM.getIdCRM());
					mbConsulta.setIdCliente(clienteCRM.getIdUnicoCliente());
					mbConsulta.setFechaRegistro(cal.getTime());
					mbConsulta.setPorcentajeConsulta(String.valueOf(porcentajeCoincidencia));
					int idConsulta = clientesDAO.insertaRegistro(mbConsulta);
					
					//INSERTA EN ing_proceso_masivo_mdb
					ClientesCRM aux = new ClientesCRM();
					aux.setIdCRM(clienteCRM.getIdCRM());
					aux.setIdConsulta(String.valueOf(idConsulta));
					aux.setFechaRegistro(dateFormat.format(cal.getTime()));
					listasDAO.insertaClienteCRM(aux);
				}
			} else {
				//dia 0 pasa por aca
				//INSERCION EN MB_CONSULTA
				MbConsultaVO mbConsulta = new MbConsultaVO();
				mbConsulta.setCalificacion(consultaVO.getCalificacion());
				mbConsulta.setIdClienteCRM(clienteCRM.getIdCRM());
				mbConsulta.setIdCliente(clienteCRM.getIdUnicoCliente());
				mbConsulta.setFechaRegistro(cal.getTime());
				mbConsulta.setPorcentajeConsulta(String.valueOf(porcentajeCoincidencia));
				int idConsulta = clientesDAO.insertaRegistro(mbConsulta);
				
				//INSERTA EN ing_proceso_masivo_mdb
				ClientesCRM aux = new ClientesCRM();
				aux.setIdCRM(clienteCRM.getIdCRM());
				aux.setIdConsulta(String.valueOf(idConsulta));
				aux.setFechaRegistro(dateFormat.format(cal.getTime()));
				listasDAO.insertaClienteCRM(aux);
			}
			String rutaArchivo = PorpertiesLoaderUtil.getInstance().getValue("pld.ruta.archivo.log") + dateFormat.format(cal.getTime() + ".txt");
		Path file = Paths.get(rutaArchivo);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
			EnvioCorreo envio = new EnvioCorreo();
			envio.enviaMailAttachment(PorpertiesLoaderUtil.getInstance().getValue("pld.mail.email"), PorpertiesLoaderUtil.getInstance().getValue("pld.mail.to.batch"), rutaArchivo);
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
}
