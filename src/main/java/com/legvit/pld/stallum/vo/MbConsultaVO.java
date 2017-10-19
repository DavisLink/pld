package com.legvit.pld.stallum.vo;

import java.util.Date;

public class MbConsultaVO {

	private int idMbConsulta;
	
	private String idClienteCRM;
	
	private int calificacion;
	
	private int porcentajeConsulta;
	
	private String idCliente;
	
	private Date fechaRegistro;
	
	private String descripcion;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdMbConsulta() {
		return idMbConsulta;
	}

	public void setIdMbConsulta(int idMbConsulta) {
		this.idMbConsulta = idMbConsulta;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public int getPorcentajeConsulta() {
		return porcentajeConsulta;
	}

	public void setPorcentajeConsulta(int porcentajeConsulta) {
		this.porcentajeConsulta = porcentajeConsulta;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	

	public String getIdClienteCRM() {
		return idClienteCRM;
	}

	public void setIdClienteCRM(String idClienteCRM) {
		this.idClienteCRM = idClienteCRM;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
}
