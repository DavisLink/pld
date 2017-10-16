package com.legvit.pld;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legvit.pld.service.ReglasService;
import com.legvit.pld.service.TercerosService;
import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.service.ClientesService;
import com.legvit.pld.stallum.service.MotorBusquedaService;
import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.stallum.vo.Persona;
import com.legvit.pld.util.PorpertiesLoaderUtil;

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

	public void execute() {
		try {
			int porcentajeEstablecido =Integer.valueOf(PorpertiesLoaderUtil.getInstance().getValue("pld.porcentaje")); 
			List<ClientesCRM> listadoClientes = clientesService.obtenListadoClientes();
			for (ClientesCRM aux : listadoClientes) {
				Persona persona = motorBusquedaService.busqCoincidenciasNombreAplicacionU(aux.getNombres() + " " + aux.getApellidoPaterno() + " " + aux.getApellidoMaterno(), 0);
				int calificacion = 0;
				float porcentaje = persona.getLista().get(0).getPorcentajeCoincidencia();
				if (porcentaje == porcentajeEstablecido) {
					calificacion = 5;
				} else {
					calificacion = 4;
				}
				reglasService.validaReglas(aux, persona.getLista().size(), porcentaje, calificacion);
			}
			tercerosService.realizaProcesoTerceros();
		} catch (PldException e) {
			e.printStackTrace();
		}
	}

}
