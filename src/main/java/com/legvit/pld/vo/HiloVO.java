package com.legvit.pld.vo;

import java.util.Date;

/**
 * Clase que contiene los datos de los hilos para realizar su registo en base de datos.
 * 
 * @author legvit
 * @version 1.0, 19102017
 */
public class HiloVO {

	/**
	 * Identificador del registro;
	 */
	private int idRegistro;

	/**
	 * N&uacute;mero de registros procesados por el hilo.
	 */
	private int registrosProcesados;

	/**
	 * Estatus que tiene el hilo con respecto a la ejecuci&oacute;n del mismo.
	 */
	private int estatus;

	/**
	 * Hora en la que inici&oacute; el hilo.
	 */
	private String horaInicio;

	/**
	 * Hora en la que finaliz&oacute; la ejecuci&oacute;n del hilo.
	 */
	private String horaFin;
	
	/**
	 * Fecha de registro.
	 */
	private Date fechaRegistro;

	public int getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(int idRegistro) {
		this.idRegistro = idRegistro;
	}

	public int getRegistrosProcesados() {
		return registrosProcesados;
	}

	public void setRegistrosProcesados(int registrosProcesados) {
		this.registrosProcesados = registrosProcesados;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}
