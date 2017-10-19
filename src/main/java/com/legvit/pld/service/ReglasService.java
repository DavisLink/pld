package com.legvit.pld.service;

import com.legvit.pld.stallum.vo.ClientesCRM;

import java.io.File;

/**
 * Contrato que define las ejecuciones de reglas del proceso de PLD
 * 
 * @author aaviac
 * @version 1.0, 14102017
 */
public interface ReglasService {

	/**
	 * Ejecuta la primera regla sobre el registro identificado por el idCRM y
	 * nombre completo recibido como par&aacute;metro.
	 * 
	 * @param idCRM
	 *            Identificador del registro del CRM.
	 * @param nombreCompleto
	 *            Nombre completo del cliente a validar.
	 */
	void validaReglas(ClientesCRM clienteCRM, int noCoincidencias, float porcentajeCoincidencia, int calificacion, File logFile);
}
