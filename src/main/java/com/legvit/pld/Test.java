package com.legvit.pld;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;

import com.legvit.pld.service.HilosService;
import com.legvit.pld.service.ReglasService;
import com.legvit.pld.service.TercerosService;
import com.legvit.pld.stallum.dao.ClientesOraDAO;
import com.legvit.pld.stallum.service.ClientesService;
import com.legvit.pld.stallum.service.MotorBusquedaService;
import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.util.EnvioCorreo;
import com.legvit.pld.util.PorpertiesLoaderUtil;
import com.legvit.pld.vo.HiloVO;

@Service
public class Test {

	@Autowired
	MotorBusquedaService motorBusquedaService;

	@Autowired
	ClientesService clientesService;
	
	@Autowired
	ReglasService reglasService;
	
	@Autowired
	TercerosService tercerosService;
	
	@Autowired
	ClientesOraDAO clientesOraDAO;
	
	@Autowired
	HilosService hilosService;

	public void execute() {
		
		File logFile = null;
		SimpleDateFormat sdfLog = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Calendar calLog = Calendar.getInstance();
		
		//OBTIENE LOS HILOS QUE FALLARON SU EJECUCION
		//ESTATUS CORRECTO
		List<HiloVO> listadoHilosError = hilosService.obtenHilosErroneos(1);
		
		if (listadoHilosError != null && !listadoHilosError.isEmpty()) {
			for (HiloVO hiloAux : listadoHilosError) {
				//OBTIENE LOS REGISTROS ERRONEOS POR HILO Y SE CONTINUA LA EJECUCION
				List<ClientesCRM> listado = hilosService.obtenRegistrosPorHilo(hiloAux.getIdRegistro());
				
				logFile = new File(hiloAux.getLogFile());
				if (!logFile.exists()) {
					try {
						logFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
						return;
					}
				}
				
				//CREAR EL HILO Y QUE TERMINE LA EJECUCION
				hilosService.creaHilo(listado, false, hiloAux.getIdRegistro(), logFile);
			}
		} else {
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				String rutaArchivo = PorpertiesLoaderUtil.getInstance().getValue("pld.ruta.archivo.log") + dateFormat.format(cal.getTime()) + ".txt";
				logFile = new File(rutaArchivo);
				if (logFile.createNewFile()) {
					System.out.println("Archivo creado correctamente");
				} else {
					System.out.println("Error en la creacion del archivo");
				}
				
				System.out.println("Inicio del proceso: " + sdfLog.format(calLog.getTime()));
				
				calLog = Calendar.getInstance();
				System.out.println("Iniciando obtencion de clientes: " + sdfLog.format(calLog.getTime()));
				
				List<ClientesCRM> listadoClientes = clientesService.obtenListadoClientes();
				
				calLog = Calendar.getInstance();
				System.out.println("Finalizando la obtencion de clientes: " + sdfLog.format(calLog.getTime()));
				
				int noHilos = Integer.valueOf(PorpertiesLoaderUtil.getInstance().getValue("pld.no.hilos"));
				int totalRegistros = listadoClientes.size();
				
				//SE REALIZA LA DIVISION DEL NO DE HILOS / EL TOTAL DE REGISTROS PARA SABER EL NUMERO DE REGISTROS QUE SE LANZARAN POR CADA HILO
				double division = totalRegistros / noHilos;
				
				//LOS INDICES QUE CONTROLARAN LAS SUBCOLECCIONES SE INICIA EN 0 Y EL FINAL ES EL RESULTADO DE LA DIVISION - 1 PUESTO QUE EL CONTEO ES BASADO EN 0
				int indiceInicial = 0;
				int indiceFinal = (int) division - 1;
				
				ArrayList<Thread> listadoHilos = new ArrayList<Thread>();
				for (int i = 0; i < noHilos; i++) {
					
					//SI ES EL ULTIMO HILO SE ENVIAN TODOS LOS DATOS RESTANTES
					if (i < noHilos - 1) {
						indiceFinal = totalRegistros - 1;
					}
					//SE OBTIENE EL SUBARREGLO DE LA COLECCION INICIAL DE REGISTROS
					ClientesCRM[] arr = (ClientesCRM[]) Arrays.copyOfRange(listadoClientes.toArray(), indiceInicial, indiceFinal);
					List<ClientesCRM> listado = Arrays.asList(arr);

					Thread tProcesa = hilosService.creaHilo(listado, true, i, logFile);
					
					//SE AGREGA EL HILO AL LISTADO DE HILOS PARA REALIZAR LA VALIDACION ACERCA DE SI EL HILO TERMINO DE EJECUTARSE
					listadoHilos.add(tProcesa);
					
					//SE INCREMENTAN LOS INDICES INICIAL Y FINAL POR EL TOTAL DE REGISTOS POR HILO
					indiceInicial += division;
					indiceFinal += division;
				}
				calLog = Calendar.getInstance();
				System.out.println("Se lanzaron " + listadoHilos.size() + " hilos exitosamente, " + sdfLog.format(calLog.getTime()));
				
				//VALIDACION DEL TERMINO DE TODOS LOS HILOS PARA CONTINUAR CON EL FLUJO DEL PROCESO
				boolean termino = false;
				while(!termino) {
					termino = true;
					for (int i = 0; i < listadoHilos.size(); i++) {
						if (listadoHilos.get(i).isAlive()) {
							termino = false;
							break;
						}
					}
				}
				
				calLog = Calendar.getInstance();
				System.out.println("Se inicia proceso de envio de correo: " + sdfLog.format(calLog.getTime()));
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		EnvioCorreo envio = null;
		try {
			envio = new EnvioCorreo();
			envio.enviaMailAttachment(PorpertiesLoaderUtil.getInstance().getValue("pld.mail.email"), PorpertiesLoaderUtil.getInstance().getValue("pld.mail.to.batch"), logFile.getAbsolutePath());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		
		calLog = Calendar.getInstance();
		System.out.println("Iniciando proceso de terceros: " + sdfLog.format(calLog.getTime()));
		tercerosService.realizaProcesoTerceros();
		calLog = Calendar.getInstance();
		System.out.println("Finalizando proceso de terceros: " + sdfLog.format(calLog.getTime()));
		
		calLog = Calendar.getInstance();
		System.out.println("Termino del proceso: " + sdfLog.format(calLog.getTime()));
	}
}
