package com.legvit.pld.quartz;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.legvit.pld.service.ReglasService;
import com.legvit.pld.service.TercerosService;
import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.service.ClientesService;
import com.legvit.pld.stallum.service.MotorBusquedaService;
import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.stallum.vo.Persona;
import com.legvit.pld.util.EnvioCorreo;
import com.legvit.pld.util.PorpertiesLoaderUtil;

public class ExecuteBatchQuartz {

	@Autowired
	MotorBusquedaService motorBusquedaService;
	
	@Autowired
	ClientesService clientesService;
	
	@Autowired
	ReglasService reglasService;
	
	@Autowired
	TercerosService tercerosService;
	
	public void execute() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			String rutaArchivo = PorpertiesLoaderUtil.getInstance().getValue("pld.ruta.archivo.log") + dateFormat.format(cal.getTime()) + ".txt";
			File logFile = new File(rutaArchivo);
			if (logFile.createNewFile()) {
				System.out.println("Archivo creado correctamente");
			} else {
				System.out.println("Error en la creacion del archivo");
			}
			SimpleDateFormat sdfLog = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			Calendar calLog = Calendar.getInstance();
			System.out.println("Inicio del proceso: " + sdfLog.format(calLog.getTime()));
			
			int porcentajeEstablecido = Integer.valueOf(PorpertiesLoaderUtil.getInstance().getValue("pld.porcentaje")); 
			
			calLog = Calendar.getInstance();
			System.out.println("Iniciando obtencion de clientes: " + sdfLog.format(calLog.getTime()));
			List<ClientesCRM> listadoClientes = clientesService.obtenListadoClientes();
			calLog = Calendar.getInstance();
			System.out.println("Finalizo proceso de obtencion de clientes: " + sdfLog.format(calLog.getTime()));
			for (ClientesCRM aux : listadoClientes) {
				calLog = Calendar.getInstance();
				System.out.println("Registro : " + aux.getIdCRM() + " inicio de procesamiento: " + sdfLog.format(calLog.getTime()));
				Persona persona = null;
				try {
					persona = motorBusquedaService.busqCoincidenciasNombreAplicacionU(aux.getNombres() + " " + aux.getApellidoPaterno() + " " + aux.getApellidoMaterno(), 0);
				
				int calificacion = 0;
				float porcentaje = persona.getLista().get(0).getPorcentajeCoincidencia();
				if (porcentaje >= porcentajeEstablecido) {
					calificacion = 5;
				} else {
					calificacion = 4;
				}
				reglasService.validaReglas(aux, persona.getLista().get(0).getCoincidencias().getCoincidencia().size(), porcentaje, calificacion, logFile);
				} catch(Exception ex) {
					ex.printStackTrace();
					continue;
				}
				calLog = Calendar.getInstance();
				System.out.println("Registro : " + aux.getIdCRM() + " termino de procesamiento: " + sdfLog.format(calLog.getTime()));
			}
			
			try {
				EnvioCorreo envio = new EnvioCorreo();
				envio.enviaMailAttachment(PorpertiesLoaderUtil.getInstance().getValue("pld.mail.email"), PorpertiesLoaderUtil.getInstance().getValue("pld.mail.to.batch"), rutaArchivo);
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
			tercerosService.realizaProcesoTerceros();
			calLog = Calendar.getInstance();
			System.out.println("Termino del proceso: " + sdfLog.format(calLog.getTime()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
