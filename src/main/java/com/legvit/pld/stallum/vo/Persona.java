package com.legvit.pld.stallum.vo;

import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Ruben Ramírez
 */
@XmlType(propOrder={"folio","nombre","apellidoPaterno","apellidoMaterno","tipo","lista"})
public class Persona {
    private String folio;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String tipo;
    private List<ListaPld> lista;

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<ListaPld> getLista() {
        return lista;
    }

    public void setLista(List<ListaPld> lista) {
        this.lista = lista;
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
    
}
