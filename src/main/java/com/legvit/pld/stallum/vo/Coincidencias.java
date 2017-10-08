package com.legvit.pld.stallum.vo;

import java.util.List;

/**
 *
 * @author Ruben Ram&iacute;rez
 */
public class Coincidencias {
    
    private List<Coincidencia> coincidencia;

    public List<Coincidencia> getCoincidencia() {
        return coincidencia;
    }

    public void setCoincidencia(List<Coincidencia> coincidencia) {
        this.coincidencia = coincidencia;
    }
    
    public float getPorcentajeMayor(){
        float porcentaje = 0.00f;
        if(coincidencia.size() > 0)
            for(Coincidencia coin:coincidencia){    
                if(coin.getPorcentaje()>porcentaje)porcentaje = coin.getPorcentaje();
            }
        return porcentaje;
    }

    
}
