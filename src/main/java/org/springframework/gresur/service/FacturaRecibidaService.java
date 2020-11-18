package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.repository.FacturaRecibidaRepository;
import org.springframework.stereotype.Service;

@Service
public class FacturaRecibidaService extends FacturaService<FacturaRecibida, FacturaRecibidaRepository>{

	@Autowired
	public FacturaRecibidaService(FacturaRecibidaRepository frRepo) {
		super.facturaRepo = frRepo;
	}
}