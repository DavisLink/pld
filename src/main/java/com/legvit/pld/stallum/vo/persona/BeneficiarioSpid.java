package com.legvit.pld.stallum.vo.persona;

import java.util.ArrayList;
import java.util.List;

import com.legvit.pld.stallum.vo.Persona;

/**
 *
 * @author Jesús Mateos
 */
public class BeneficiarioSpid extends Persona {
    
    public BeneficiarioSpid(){
        
    }
    public BeneficiarioSpid(com.mx.investa.crm.servicioweb.BeneficiarioSpid beneficiarioSpid ){
        this.setNombre(beneficiarioSpid.getIngBeneficiario());
        this.setApellidoPaterno("");
        this.setApellidoMaterno("");
    }
    
    
}
