package com.legvit.pld.stallum.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legvit.pld.stallum.comun.Comun;
import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.dao.ClientesDAO;
import com.legvit.pld.stallum.dao.ListasDAO;
import com.legvit.pld.stallum.vo.Coincidencia;
import com.legvit.pld.stallum.vo.Coincidencias;
import com.legvit.pld.stallum.vo.ListaPld;
import com.legvit.pld.stallum.vo.ListasAll;


/**
 *
 * @author mcervantes
 */
@Service(value="listasAllService")
public class ListasAllServiceImpl implements ListasAllService {

    private float porcentajeMinimoCoinc = 0.00f;
    private int mode;
    
    @Autowired
    ListasDAO listasDao;
    
    @Autowired 
    ClientesDAO clientesDAO;
    
    public ListasAllServiceImpl() throws PldException{
        porcentajeMinimoCoinc = Float.valueOf("98");
    }
//    
    public ListasAllServiceImpl(int porcentajeMinCoinc) throws PldException{
        porcentajeMinimoCoinc = Float.valueOf(porcentajeMinCoinc);
    }

    public void setPorcentajeMinimoCoinc(int porcentajeMinCoinc) {
    	porcentajeMinimoCoinc = Float.valueOf(porcentajeMinCoinc);
    }
    
    public List<ListasAll> getListaAll(String nombre) throws PldException {
        return clientesDAO.findByNameAll(nombre);
    }
    
    public ListaPld doBusquedaCoincidenciaAll(String nombre, List<ListasAll> listaNegra, String nombreList, List<String> sociedadesMercantiles) throws PldException {
        ListaPld listaPld = new ListaPld();
        float porcentaje = 0f;
        Coincidencias coincidencias = new Coincidencias();
        Coincidencia coincidenciaNueva;
        List<Coincidencia> listaCoincidencias = new ArrayList<Coincidencia>();
        NameCompare nameCompare = new NameCompare();
        //DaoCatalogos daoCatalogos = new DaoCatalogos();
        // Se recorre cada elemento de la lista 
        try {
            //List<String> sociedadesMercantiles = daoCatalogos.getSociedadesMercantiles();
            nombre = Comun.removeSpecialChars(nombre);
            nombre = Comun.removeArticles(nombre);
            nombre = Comun.removeSociedadMercantil(nombre, sociedadesMercantiles);
            for (ListasAll elemento : listaNegra) {
                // Se realiza la comparación de los nombres para obtener el porcentaje

                porcentaje = (float) nameCompare.nameCompare(nombre, elemento.getNombre(), mode);
                // Si el porcentaje iguala o supera el mínimo establecido se guarda la coincidencia
                if (porcentaje > porcentajeMinimoCoinc) {
                    coincidenciaNueva = new Coincidencia();
                    coincidenciaNueva.setNombre(elemento.getNombreOriginal());
                    coincidenciaNueva.setFolio(elemento.getFolio());
                    coincidenciaNueva.setPorcentaje(porcentaje);
                    listaCoincidencias.add(coincidenciaNueva);
                }

            }
            Collections.sort(listaCoincidencias);
            coincidencias.setCoincidencia(listaCoincidencias);
            listaPld.setPorcentajeCoincidencia(coincidencias.getPorcentajeMayor());
            listaPld.setCoincidencias(coincidencias);
            listaPld.setNombre(nombreList);
        } catch (Exception e) {
            throw new PldException("Error: " + e.getMessage(), e);
        }

        return listaPld;
    }

}
