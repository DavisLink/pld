package com.legvit.pld.vo;

import java.util.Date;

public class ControlPLDVO {
	
	private int id;
	
	private String idCRM;
	
	private String nombreCompleto;
	
	private Date fechaRegistro;
	
	private int calificacion;
	
	private int noCoincidencias;
	
	public int getNoCoincidencias() {
		return noCoincidencias;
	}

	public void setNoCoincidencias(int noCoincidencias) {
		this.noCoincidencias = noCoincidencias;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdCRM() {
		return idCRM;
	}

	public void setIdCRM(String idCRM) {
		this.idCRM = idCRM;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
}
