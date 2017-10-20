package com.legvit.pld.task;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.legvit.pld.service.ReglasService;
import com.legvit.pld.stallum.service.MotorBusquedaService;
import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.stallum.vo.Persona;
import com.legvit.pld.util.PorpertiesLoaderUtil;

/**
 * Job que realiza el procesamiento de PLD
 * 
 * @author legvit
 * @version 1.0, 19102017
 */
public class Procesar implements Runnable {
	
	private List<ClientesCRM> listadoClientes;
	
	private MotorBusquedaService motorBusquedaService;
	
	private ReglasService reglasService;
	
	public Procesar() {}
	
	/**
	 * Constructor de la clase en el que se reciben los par&aacute;metros para
	 * realizar la inicializaci&oacute;n de las variables de instancia.
	 * 
	 * @param listadoClientes
	 *            listado que deber&aacute; procesar el hilo.
	 * @param motorBusquedaService
	 *            Instancia del objeto de negocio.
	 * @param reglasService
	 *            Instancia del objeto de negocio.
	 */
	public Procesar(List<ClientesCRM> listadoClientes, MotorBusquedaService motorBusquedaService, ReglasService reglasService) {
		this.listadoClientes = listadoClientes; 
		this.motorBusquedaService = motorBusquedaService;
		this.reglasService = reglasService;
	}

	/**
	 * M&eacute;todo que realiza la ejecuci&oacute;n del procesamiento de PLD
	 */
	public void run() {
		System.out.println("Realizando el procesamiento ...");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String rutaArchivo = PorpertiesLoaderUtil.getInstance().getValue("pld.ruta.archivo.log") + dateFormat.format(cal.getTime()) + ".txt";
		File logFile = new File(rutaArchivo);
		try {
			if (logFile.createNewFile()) {
				System.out.println("Archivo creado correctamente");
			} else {
				System.out.println("Error en la creacion del archivo");
			}	
		} catch (IOException io) {
			io.printStackTrace();
		}
		
		SimpleDateFormat sdfLog = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Calendar calLog = Calendar.getInstance();
		System.out.println("Inicio del proceso: " + sdfLog.format(calLog.getTime()));
		
		int porcentajeEstablecido = Integer.valueOf(PorpertiesLoaderUtil.getInstance().getValue("pld.porcentaje")); 
		
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
		
	}
	
	public List<ClientesCRM> getListadoClientes() {
		return listadoClientes;
	}

	public void setListadoClientes(List<ClientesCRM> listadoClientes) {
		this.listadoClientes = listadoClientes;
	}
}
