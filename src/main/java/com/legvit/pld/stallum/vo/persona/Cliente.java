package com.legvit.pld.stallum.vo.persona;

import java.util.List;

import com.legvit.pld.stallum.vo.Accionistas;
import com.legvit.pld.stallum.vo.Beneficiarios;
import com.legvit.pld.stallum.vo.BeneficiariosSpid;
import com.legvit.pld.stallum.vo.Persona;
import com.legvit.pld.stallum.vo.RepresentantesLegales;

/**
 *
 * @author Ruben Ramírez
 */
public class Cliente extends Persona {
    private Accionistas accionistas;
    private RepresentantesLegales representantesLegales;
    private Beneficiarios beneficiarios;
    private BeneficiariosSpid beneficiariosSpid;
    private List<Pep> peps;

    public Cliente(){
        
    }

    public Cliente(Persona persona){
        this.setLista(persona.getLista());
        this.setNombre(persona.getNombre());
        this.setFolio(persona.getFolio());
        this.setTipo(persona.getTipo());
    }

    public Accionistas getAccionistas() {
        return accionistas;
    }

    public void setAccionistas(Accionistas accionistas) {
        this.accionistas = accionistas;
    }

    public RepresentantesLegales getRepresentantesLegales() {
        return representantesLegales;
    }

    public void setRepresentantesLegales(RepresentantesLegales representantesLegales) {
        this.representantesLegales = representantesLegales;
    }

    public List<Pep> getPeps() {
        return peps;
    }

    public void setPeps(List<Pep> peps) {
        this.peps = peps;
    }

    public Beneficiarios getBeneficiarios() {
        return beneficiarios;
    }

    public void setBeneficiarios(Beneficiarios beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    public BeneficiariosSpid getBeneficiariosSpid() {
        return beneficiariosSpid;
    }

    public void setBeneficiariosSpid(BeneficiariosSpid beneficiariosSpid) {
        this.beneficiariosSpid = beneficiariosSpid;
    }

}
