package com.legvit.pld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legvit.pld.stallum.dao.ClientesOraDAO;
import com.legvit.pld.vo.HiloVO;

/**
 * Clase que implementa el contrato de la interface <code>HilosService</code>
 * 
 * @author eduardo_colin
 * @version 1.0, 19102017
 */
@Service(value="hilosService")
public class HilosServiceImpl implements HilosService {
	
	@Autowired
	ClientesOraDAO clientesOraDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertaHilo(HiloVO hiloVO) {
		clientesOraDAO.insertaHilo(hiloVO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actualizaHilo(int estatus, String horaTermino, int idRegistro) {
		clientesOraDAO.actualizaHilo(estatus, horaTermino, idRegistro);
	}
}
