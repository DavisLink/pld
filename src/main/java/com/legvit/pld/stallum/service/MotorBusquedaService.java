package com.legvit.pld.stallum.service;

import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.vo.Persona;

import java.util.List;

/**
 *
 * @author Ruben Ramírez
 */
public interface MotorBusquedaService {
    
   Persona busqCoincidenciasNombreAplicacionU(String nombre, int porcentajeMinCoinc) throws PldException;
   
}
