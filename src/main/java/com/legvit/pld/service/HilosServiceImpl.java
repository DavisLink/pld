package com.legvit.pld.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legvit.pld.stallum.dao.ClientesOraDAO;
import com.legvit.pld.stallum.service.ClientesService;
import com.legvit.pld.stallum.service.MotorBusquedaService;
import com.legvit.pld.stallum.vo.ClientesCRM;
import com.legvit.pld.task.Procesar;
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
	
	@Autowired
	MotorBusquedaService motorBusquedaService;

	@Autowired
	ClientesService clientesService;
	
	@Autowired
	ReglasService reglasService;
	
	@Autowired
	TercerosService tercerosService;
	
	@Autowired
	HilosService hilosService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int insertaHilo(HiloVO hiloVO) {
		return clientesOraDAO.insertaHilo(hiloVO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actualizaHilo(int estatus, String horaTermino, int idRegistro) {
		clientesOraDAO.actualizaHilo(estatus, horaTermino, idRegistro);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertaReplicaRegistro(ClientesCRM clienteCRM) {
		clientesOraDAO.insertaReplicaRegistro(clienteCRM);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HiloVO> obtenHilosErroneos(int idEstatus) {
		return clientesOraDAO.obtenHilosErroneos(idEstatus);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ClientesCRM> obtenRegistrosPorHilo(int idHilo) {
		return clientesOraDAO.obtenRegistrosPorHilo(idHilo);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Thread creaHilo(List<ClientesCRM> listado, boolean insertaListado, int noHilo, File logFile) {
		Calendar calHilo = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date hilo = calHilo.getTime();
		
		//INSERTAR HILO EN BITACORA DE HILOS
		HiloVO hiloVO = new HiloVO();
		hiloVO.setRegistrosProcesados(listado.size());
		//ESTATUS 0 - INICIADO
		hiloVO.setEstatus(0);
		hiloVO.setHoraInicio(sdf.format(hilo));
		hiloVO.setFechaRegistro(hilo);
		int idHilo = insertaHilo(hiloVO);
		
		Thread tProcesa = new Thread(new Procesar(listado, motorBusquedaService, reglasService, clientesOraDAO, idHilo, logFile, hilosService));
		tProcesa.setName("Thread_PLD_" + noHilo);
		
		if (insertaListado) {
			insertaRegistrosListado(listado, idHilo);	
		}
		
		tProcesa.start();
		return tProcesa;
	}
	
	public void insertaRegistrosListado(List<ClientesCRM> listado, int idHilo) {
		for (ClientesCRM aux : listado) {
			ClientesCRM clienteCRM = new ClientesCRM();
			clienteCRM.setApellidoMaterno(aux.getApellidoMaterno());
			clienteCRM.setApellidoPaterno(aux.getApellidoPaterno());
			clienteCRM.setCalificacion(aux.getCalificacion());
			clienteCRM.setFechaRegistro(aux.getFechaRegistro());
			clienteCRM.setIdConsulta(aux.getIdConsulta());
			clienteCRM.setIdCRM(aux.getIdCRM());
			clienteCRM.setIdHilo(aux.getIdHilo());
			clienteCRM.setIdUnicoCliente(aux.getIdUnicoCliente());
			clienteCRM.setNombres(aux.getNombres());
			clienteCRM.setProcesado(aux.isProcesado());
			clienteCRM.setTipoCliente(aux.getTipoCliente());
			clienteCRM.setTipoPersona(aux.getTipoPersona());
			clienteCRM.setTipoRazonSocial(aux.getTipoRazonSocial());
			clienteCRM.setIdHilo(idHilo);
			insertaReplicaRegistro(clienteCRM);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actualizaEstatusRegistroReplica(String idCRM, int estatus) {
		clientesOraDAO.actualizaEstatusRegistroReplica(idCRM, estatus);
	}
}
