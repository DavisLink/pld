package com.legvit.pld.stallum.dao;

import java.util.List;

import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.stallum.vo.ListasAll;

public interface ListasDAO {

	List<ListasAll> findByNameAll(String name) throws PldException;
	
	List<ClientesCRM> obtenClientesCRM();
}