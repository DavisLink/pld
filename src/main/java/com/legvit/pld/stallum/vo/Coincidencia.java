package com.legvit.pld.stallum.vo;

import com.legvit.pld.stallum.vo.lista.ListaGris;
import com.legvit.pld.stallum.vo.lista.ListaNegra;
import com.legvit.pld.stallum.vo.lista.ListaSiti;
import com.legvit.pld.stallum.vo.lista.ListaWorldCheck;

/**
 *
 * @author Ruben Ram&iacute;rez
 */
public class Coincidencia implements Comparable{
    private String folio;
    private String nombre;
    private Tokens tokens;
    private float porcentaje;

    public Coincidencia(){

    }
    
    @Override
    public int compareTo(Object o) {
        Coincidencia c = (Coincidencia)o;
        if (porcentaje < c.getPorcentaje()) {
            return 1;
        }
        if (porcentaje > c.getPorcentaje()) {
            return -1;
        }
        return 0;
    }


    public Coincidencia(ListaNegra listaNegra){
        this.nombre = listaNegra.getNombre();
    }
    
    public Coincidencia(ListaGris listaGris){
        this.nombre = listaGris.getNombre();
    }

    public Coincidencia(ListaSiti listaSiti){
        this.nombre = listaSiti.getNombre();
    }
    
    public Coincidencia(ListaWorldCheck listaWorldCheck) {
        this.nombre = listaWorldCheck.getNombre();
    }

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

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Tokens getTokens() {
        return tokens;
    }

    public void setTokens(Tokens tokens) {
        this.tokens = tokens;
    }

}
