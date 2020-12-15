package org.springframework.gresur.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.repository.FacturaRecibidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaRecibidaService extends FacturaService<FacturaRecibida, FacturaRecibidaRepository>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	public FacturaRecibidaService(FacturaRecibidaRepository frRepo) {
		super.facturaRepo = frRepo;
	}
	
	@Transactional
	public Long count() {
		return super.count();
	}
	
	@Transactional
	public FacturaRecibida save(FacturaRecibida facturaRecibida) throws DataAccessException {
		FacturaRecibida ret = facturaRepo.save(facturaRecibida);
		em.flush();
		return ret;
	}
}