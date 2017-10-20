package com.legvit.pld.stallum.vo;

public class ClientesCRM {

	private String idCRM;
	
	private String idUnicoCliente;
	
	private String nombres;
	
	private String apellidoPaterno;
	
	private String apellidoMaterno;
	
	private String tipoRazonSocial;
	
	private String tipoPersona;
	
	private String tipoCliente;
	
	private String idConsulta;
	
	private String fechaRegistro;
	
	private int calificacion;
	
	private int idHilo;
	
	private boolean procesado;
	
	public int getIdHilo() {
		return idHilo;
	}

	public void setIdHilo(int idHilo) {
		this.idHilo = idHilo;
	}

	public boolean isProcesado() {
		return procesado;
	}

	public void setProcesado(boolean procesado) {
		this.procesado = procesado;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(String idConsulta) {
		this.idConsulta = idConsulta;
	}

	public String getIdCRM() {
		return idCRM;
	}

	public void setIdCRM(String idCRM) {
		this.idCRM = idCRM;
	}

	public String getIdUnicoCliente() {
		return idUnicoCliente;
	}

	public void setIdUnicoCliente(String idUnicoCliente) {
		this.idUnicoCliente = idUnicoCliente;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getTipoRazonSocial() {
		return tipoRazonSocial;
	}

	public void setTipoRazonSocial(String tipoRazonSocial) {
		this.tipoRazonSocial = tipoRazonSocial;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	
}
