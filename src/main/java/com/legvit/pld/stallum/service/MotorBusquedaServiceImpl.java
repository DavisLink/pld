package com.legvit.pld.stallum.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.dao.ClientesDAO;
import com.legvit.pld.stallum.vo.Coincidencia;
import com.legvit.pld.stallum.vo.ListaPld;
import com.legvit.pld.stallum.vo.ListasAll;
import com.legvit.pld.stallum.vo.Persona;
import com.legvit.pld.stallum.vo.lista.ListaGris;
import com.legvit.pld.stallum.vo.lista.ListaNegra;
import com.legvit.pld.stallum.vo.persona.Cliente;

/**
 *
 * @author Ruben Ramírez
 */
@Service(value="motorBusquedaService")
public class MotorBusquedaServiceImpl implements MotorBusquedaService {

    List<Coincidencia> listaNegraCoincidencias = null;
    List<Coincidencia> listaGrisCoincidencias = null;
    List<Coincidencia> listaSitiCoincidencias = null;
    List<Coincidencia> listaWorldCheckCoincidencias = null;
    List<ListaNegra> listaNegra;
    List<ListaGris> listaGris;
    
    @Autowired
    ClientesDAO clientesDAO;
    
    public Persona busqCoincidenciasNombreAplicacionU(String nombre, int porcentajeMinCoinc) throws PldException {
        //Listas
        Cliente cliente = new Cliente();
        Persona persona = new Persona();
        try {
            cliente.setNombre(nombre);
            busqCoincAlgoritmoNombreAplicacionU(cliente, porcentajeMinCoinc);
            persona = (Persona) cliente;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PldException(e.getMessage());
        }
        return persona;
    }
    public void busqCoincAlgoritmoNombreAplicacionU(Cliente cliente, int porcentajeMinCoinc) throws PldException {
    	
    	List<String> sociedadesMercantiles = clientesDAO.obtenSociedadesMercantiles(); 

        //RespuestaBusqCoincidencia respuesta = new RespuestaBusqCoincidencia();
        System.out.println("  ============getListas Concidencias========");
        /*ListaNegraService listaNegraService = new ListaNegraServiceImpl(porcentajeMinCoinc);
        ListaGrisService listaGrisService = new ListaGrisServiceImpl(porcentajeMinCoinc);
        ListaSitiService listaSitiService = new ListaSitiServiceImpl1(porcentajeMinCoinc);
        ListaWorldCheckService listaWorldCheckService = new ListaWorldCheckServiceImpl(porcentajeMinCoinc);*/
        //ListasAllService listaAll = new ListasAllServiceImpl(porcentajeMinCoinc);
        ListasAllService listaAll = new ListasAllServiceImpl(porcentajeMinCoinc);
        System.out.println("  ============Fin getListas Concidencias========");

        //Cliente
        List<ListaPld> listaDeListaPld = new ArrayList<ListaPld>();
        ListaPld listaResultado = new ListaPld();
        String nombreCliente = cliente.getNombre();
       /* List<String> sociedadesMercantiles = new ArrayList<String>();
        try {
            DaoCatalogos daoCatalogos = new DaoCatalogos();
            sociedadesMercantiles = daoCatalogos.getSociedadesMercantiles();
        } catch (Exception ex) {
            Logger.getLogger(MotorBusquedaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //Lista All
        System.out.println("Lista ALL");
        List<ListasAll> listaAllCliente = new ArrayList<ListasAll>();
        listaAllCliente = listaAll.getListaAll(nombreCliente);
        System.out.println("Lista ALL");
        List<ListasAll> ListaN = new ArrayList<ListasAll>();
        List<ListasAll> ListaG = new ArrayList<ListasAll>();
        List<ListasAll> ListaS = new ArrayList<ListasAll>();
        List<ListasAll> ListaW = new ArrayList<ListasAll>();
       System.out.println("Ordenar listas por tipo");
        for (ListasAll elemento : listaAllCliente) {
            if (elemento.getTipo() == 1) {
                ListaN.add(elemento);
            } else if (elemento.getTipo() == 2) {
                ListaG.add(elemento);
            } else if (elemento.getTipo() == 3) {
                ListaS.add(elemento);
            } else {
                ListaW.add(elemento);
            }
        }
       System.out.println("Ordenar listas por tipo");
      // System.out.println(">>> PROCESAMIENTO DE LA LISTAS...");
        if (ListaN.size() > 0) {
           System.out.println(">>> PROCESAMIENTO DE LA LISTA NEGRA..."+ListaN.size());
           // System.out.println(">>> " + new Date());
            listaResultado = listaAll.doBusquedaCoincidenciaAll(nombreCliente, ListaN, "LISTA NEGRA", sociedadesMercantiles);
            listaDeListaPld.add(listaResultado);
          System.out.println(">>> FIN PROCESAMIENTO DE LA LISTA NEGRA...");
           //System.out.println(">>> " + new Date());
        }

        if (ListaG.size() > 0) {
           System.out.println(">>> PROCESAMIENTO DE LA LISTA Gris..."+ListaG.size());
            //System.out.println(">>> " + new Date());
            listaResultado = listaAll.doBusquedaCoincidenciaAll(nombreCliente, ListaG, "LISTA GRIS", sociedadesMercantiles);
            listaDeListaPld.add(listaResultado);
           System.out.println(">>> FIN PROCESAMIENTO DE LA LISTA Gris...");
            //System.out.println(">>> " + new Date());
        }
        if (ListaS.size() > 0) {
           System.out.println(">>> PROCESAMIENTO DE LA LISTA Siti..."+ListaS.size());
            //System.out.println(">>> " + new Date());
            listaResultado = listaAll.doBusquedaCoincidenciaAll(nombreCliente, ListaS, "LISTA SITI", sociedadesMercantiles);
            listaDeListaPld.add(listaResultado);
           System.out.println(">>> FIN PROCESAMIENTO DE LA LISTA Siti...");
            //System.out.println(">>> " + new Date());
        }
        if (ListaW.size() > 0) {
           System.out.println(">>> PROCESAMIENTO DE LA LISTA WorldCheck..."+ListaW.size());
            //System.out.println(">>> " + new Date());
            listaResultado = listaAll.doBusquedaCoincidenciaAll(nombreCliente, ListaW, "LISTA WORLDCHECK", sociedadesMercantiles);
            listaDeListaPld.add(listaResultado);
           System.out.println(">>> FIN PROCESAMIENTO DE LA LISTA WorldCheck...");
            //System.out.println(">>> " + new Date());
        }
        //System.out.println(">>> PROCESAMIENTO DE LA LISTAS...");
        cliente.setLista(listaDeListaPld);

    }
}
