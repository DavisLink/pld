package com.legvit.pld.stallum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legvit.pld.stallum.dao.ClientesDAO;
import com.legvit.pld.stallum.dao.ListasDAO;
import com.legvit.pld.stallum.vo.ClientesCRM;

@Service(value="clientesService")
public class ClientesServiceImpl implements ClientesService {

	@Autowired
	ListasDAO listasDao;
	
	public List<ClientesCRM> obtenListadoClientes() {
		return listasDao.obtenClientesCRM();
	}
}
