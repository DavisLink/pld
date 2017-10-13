package com.legvit.pld.quartz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.service.ClientesService;
import com.legvit.pld.stallum.service.MotorBusquedaService;
import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.stallum.vo.Persona;

public class ExecuteBatchQuartz {

	@Autowired
	MotorBusquedaService motorBusquedaService;
	
	@Autowired
	ClientesService clientesService;
	
	public void execute() {
		System.out.println("Hola desde el cron ...");
		
		try {
			List<ClientesCRM> listadoClientes = clientesService.obtenListadoClientes();
			
			for (ClientesCRM aux : listadoClientes) {
				Persona persona = motorBusquedaService.busqCoincidenciasNombreAplicacionU(aux.getNombres() + " " + aux.getApellidoPaterno() + " " + aux.getApellidoMaterno(), 98);
				//...
			}
			
		} catch (PldException e) {
			e.printStackTrace();
		}
	}
}
