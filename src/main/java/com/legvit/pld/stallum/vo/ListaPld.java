package com.legvit.pld.stallum.vo;

import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Ruben Ramírez
 */
@XmlType(propOrder={"nombre","porcentajeCoincidencia","coincidencias"})
public class ListaPld {
    private String nombre;
    private float porcentajeCoincidencia;
    private Coincidencias coincidencias;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPorcentajeCoincidencia() {
        return porcentajeCoincidencia;
    }

    public void setPorcentajeCoincidencia(float porcentajeCoincidencia) {
        this.porcentajeCoincidencia = porcentajeCoincidencia;
    }

    public Coincidencias getCoincidencias() {
        return coincidencias;
    }

    public void setCoincidencias(Coincidencias coincidencias) {
        this.coincidencias = coincidencias;
    }

}
