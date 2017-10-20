package com.legvit.pld.service;

import com.legvit.pld.vo.HiloVO;

/**
 * Contrato que define las ejecuciones de reglas del proceso de PLD
 * 
 * @author aaviac
 * @version 1.0, 14102017
 */
public interface HilosService {

	/**
	 * Realiza la inserci&oacute;n del registro correspondiente al hilo en
	 * ejecuci&oacute;n
	 * 
	 * @param hiloVO
	 *            Value object que contiene los datos del hilo ejecutado.
	 */
	void insertaHilo(HiloVO hiloVO);
	
	/**
	 * Realiza la actualizaci&oacute;n del registro correspondiente al hilo en el
	 * registro.
	 * 
	 * @param estatus
	 *            estatus del hilo.
	 * @param horaTermino
	 *            hora de t&eacute;rmino de ejecuci&oacute;n del hilo.
	 * @param idRegistro
	 *            identificador del hilo en la base de datos.
	 */
	void actualizaHilo(int estatus, String horaTermino, int idRegistro);
}
