package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.repository.FacturaRecibidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaRecibidaService extends FacturaService<FacturaRecibida, FacturaRecibidaRepository>{

	@Autowired
	public FacturaRecibidaService(FacturaRecibidaRepository frRepo) {
		super.facturaRepo = frRepo;
	}
	
	@Transactional
	public FacturaRecibida add(FacturaRecibida recibida) throws DataAccessException{
		return facturaRepo.save(recibida);
	}
}