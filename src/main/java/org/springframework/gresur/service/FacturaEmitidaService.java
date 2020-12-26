package org.springframework.gresur.service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.repository.FacturaEmitidaRepository;
import org.springframework.gresur.service.exceptions.ClienteDefaulterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaEmitidaService extends FacturaService<FacturaEmitida, FacturaEmitidaRepository>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	public FacturaEmitidaService(FacturaEmitidaRepository feRepo) {
		super.facturaRepo = feRepo;
	}
	
	@Transactional
	public FacturaEmitida save(FacturaEmitida emitida) throws DataAccessException {
		em.clear();

		Cliente cliente = emitida.getCliente();
		if(!facturaRepo.findByClienteIdAndEstaPagadaFalse(cliente.getId()).isEmpty())
			throw new ClienteDefaulterException("El cliente tiene facturas pendientes");
		
		FacturaEmitida ret = facturaRepo.save(emitida);
		em.flush();
		return ret;
	}
}