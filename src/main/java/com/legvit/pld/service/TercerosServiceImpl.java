package com.legvit.pld.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legvit.pld.stallum.dao.ClientesDAO;
import com.legvit.pld.stallum.dao.ListasDAO;
import com.legvit.pld.stallum.vo.MbConsultaVO;
import com.legvit.pld.vo.ClienteRelConsultaVO;
import com.legvit.pld.vo.RelacionadosVO;

@Service(value="tercerosService")
public class TercerosServiceImpl implements TercerosService {

	@Autowired
	ClientesDAO clientesDAO;
	
	@Autowired
	ListasDAO listasDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void realizaProcesoTerceros() {
		
		//1.- OBTENER REGISTROS DE LA TABLA mb_consulta del dia anterior
		List<MbConsultaVO> listado = clientesDAO.obtenRegistros();
		
		//2.- con cada registro obtener el id_crm y obtener los registros relacionados de la tabla
		//clienteyrelacionadosxamb
		for (MbConsultaVO consulta : listado) {
			List<RelacionadosVO> listadoRelacionado = listasDAO.obtenRelacionados(consulta.getIdClienteCRM());
			//3.- si el tipoderelacion == cli
			// insertar en mb_consulta_cliente
			
			for (RelacionadosVO aux : listadoRelacionado) {
				
				ClienteRelConsultaVO clienteRelVO = new ClienteRelConsultaVO();
				clienteRelVO.setIdMbConsulta(consulta.getIdMbConsulta());
				clienteRelVO.setNombreCliente(aux.getNombreRazonSocialRelacionado());
				clienteRelVO.setApPaternoCliente(aux.getApePaternoRelacionado());
				clienteRelVO.setApMaternoCliente(aux.getApeMaternoRelacionado());
				
				if (aux.getTipoRelacionConClienteRelacionado().contains("CLI")) {
					//SE INSERTA EN mb_consulta_cliente
					clientesDAO.insertaRegistroClienteRel(clienteRelVO);
				} else {
					//SE INSERTA EN mb_consulta_beneficiario
					clientesDAO.insertaRegistroBenefRel(clienteRelVO);
				}
			}
		}
	}
}
