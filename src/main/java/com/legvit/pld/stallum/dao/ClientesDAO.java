package com.legvit.pld.stallum.dao;

import java.util.List;

import com.legvit.pld.stallum.comun.PldException;
import com.legvit.pld.stallum.vo.ListasAll;
import com.legvit.pld.stallum.vo.MbConsultaVO;
import com.legvit.pld.vo.ClienteRelConsultaVO;

public interface ClientesDAO {

	List<String> obtenSociedadesMercantiles();

	List<ListasAll> findByNameAll(String name) throws PldException;
	
	
}
