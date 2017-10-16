package com.legvit.pld.stallum.vo;

import java.util.Date;

public class MbConsultaVO {

	private String idMbConsulta;
	
	private String idClienteCRM;
	
	private String calificacion;
	
	private String porcentajeConsulta;
	
	private String idCliente;
	
	private Date fechaRegistro;
	
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getIdMbConsulta() {
		return idMbConsulta;
	}

	public void setIdMbConsulta(String idMbConsulta) {
		this.idMbConsulta = idMbConsulta;
	}

	public String getIdClienteCRM() {
		return idClienteCRM;
	}

	public void setIdClienteCRM(String idClienteCRM) {
		this.idClienteCRM = idClienteCRM;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public String getPorcentajeConsulta() {
		return porcentajeConsulta;
	}

	public void setPorcentajeConsulta(String porcentajeConsulta) {
		this.porcentajeConsulta = porcentajeConsulta;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
}
