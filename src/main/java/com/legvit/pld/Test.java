package com.legvit.pld;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legvit.pld.service.ReglasService;
import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.service.ClientesService;
import com.legvit.pld.stallum.service.MotorBusquedaService;
import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.stallum.vo.Persona;

@Service
public class Test {

	@Autowired
	MotorBusquedaService motorBusquedaService;

	@Autowired
	ClientesService clientesService;
	
	@Autowired
	ReglasService reglasService;

	public void execute() {
		System.out.println("Hola desde el cron ...");

		try {
			List<ClientesCRM> listadoClientes = clientesService.obtenListadoClientes();

			for (ClientesCRM aux : listadoClientes) {
				Persona persona = motorBusquedaService.busqCoincidenciasNombreAplicacionU(aux.getNombres() + " " + aux.getApellidoPaterno() + " " + aux.getApellidoMaterno(), 0);
				reglasService.primeraRegla(aux, persona.getLista().size());
				
				
				System.out.println("Tamaño de la lista: " + persona.getLista().size());
				System.out.println("Nombre: " + persona.getNombre());
				System.out.println("Tipo: " + persona.getTipo());
			}

		} catch (PldException e) {
			e.printStackTrace();
		}
	}

}
