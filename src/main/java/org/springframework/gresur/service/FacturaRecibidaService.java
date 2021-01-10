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
	private ITVService ITVService;
	
	@Autowired
	private SeguroService seguroService;
	
	@Autowired
	private ReparacionService reparacionService;
	
	@Autowired
	private ConfiguracionService configService;
	
	@Autowired
	public FacturaRecibidaService(FacturaRecibidaRepository frRepo) {
		super.facturaRepo = frRepo;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	
	@Transactional(readOnly = true)
	public Long count() {
		return super.count();
	}
	
	@Transactional
	public FacturaRecibida save(FacturaRecibida facturaRecibida) throws DataAccessException {
		em.clear();
		
		if(facturaRecibida.getId() == null && facturaRecibida.esRectificativa()) {
			facturaRecibida.setFechaEmision(facturaRecibida.getOriginal().getFechaEmision());	
			facturaRecibida.setNumFactura(configService.nextValRecibidasRectificada());
		}else {
			facturaRecibida.setNumFactura(configService.nextValRecibidas());
		}
		
		FacturaRecibida ret = facturaRepo.save(facturaRecibida);
		em.flush();
		return ret;
	}
	
	@Override
	@Transactional(rollbackFor = DataAccessException.class)
	public void deleteById(Long id) throws DataAccessException {
		reparacionService.deleteByRecibidasId(id);
		seguroService.deleteByRecibidasId(id);
		ITVService.deleteByRecibidasId(id);
		facturaRepo.deleteById(id);
	}
	
	@Override
	@Transactional
	public void deleteAll() throws DataAccessException {
		reparacionService.deleteAll();
		seguroService.deleteAll();
		ITVService.deleteAll();
		facturaRepo.deleteAll();
	}
	
}