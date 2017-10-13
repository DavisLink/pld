package com.legvit.pld.stallum.service;

import java.util.List;

import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.vo.ListaPld;
import com.legvit.pld.stallum.vo.ListasAll;

public interface ListasAllService {
	
	List<ListasAll> getListaAll(String nombre) throws PldException;
	
	ListaPld doBusquedaCoincidenciaAll(String nombre, List<ListasAll> listaNegra, String nombreList, List<String> sociedadesMercantiles) throws PldException;
}
