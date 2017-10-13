package com.legvit.pld.stallum.vo.persona;

import java.util.ArrayList;
import java.util.List;

import com.legvit.pld.stallum.vo.Persona;

/**
 *
 * @author Ruben Ramírez
 */
public class Accionista extends Persona {
    
    public Accionista(){
        
    }
    public Accionista(com.mx.investa.crm.servicioweb.Accionista accionista ){
        this.setNombre(accionista.getJaNombreAccion());
        this.setApellidoPaterno(accionista.getJaApaternoAccion());
        this.setApellidoMaterno(accionista.getJaAmaternoAccion());
    }
}
