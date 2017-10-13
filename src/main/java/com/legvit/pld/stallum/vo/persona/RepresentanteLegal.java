package com.legvit.pld.stallum.vo.persona;

import com.legvit.pld.stallum.vo.Persona;

/**
 *
 * @author Ruben Ramírez
 */
public class RepresentanteLegal extends Persona {
    
    public RepresentanteLegal(){
        
    }
            
    public RepresentanteLegal(com.mx.investa.crm.servicioweb.RepresentanteLegal representanteLegal ){
        this.setNombre(representanteLegal.getJaNombreRepre());
        this.setApellidoPaterno(representanteLegal.getJaApaternoRepre());
        this.setApellidoMaterno(representanteLegal.getJaAmaternoRepre());
    }
    
}
