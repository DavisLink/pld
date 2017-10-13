package com.legvit.pld.stallum.vo.persona;

import java.util.ArrayList;
import java.util.List;

import com.legvit.pld.stallum.vo.Persona;

/**
 *
 * @author Jesús Mateos
 */
public class Beneficiario extends Persona {
    
    public Beneficiario(){
        
    }
    public Beneficiario(com.mx.investa.crm.servicioweb.Beneficiario beneficiario ){
        this.setNombre(beneficiario.getIngNombreBeneficiario());
        this.setApellidoPaterno(beneficiario.getIngBenefApPaterno());
        this.setApellidoMaterno(beneficiario.getIngBenefApMaterno());
    }
    
    
}
