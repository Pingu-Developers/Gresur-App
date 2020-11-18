package org.springframework.gresur.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.repository.FacturaEmitidaRepository;
import org.springframework.stereotype.Service;

@Service
public class FacturaEmitidaService extends FacturaService<FacturaEmitida, FacturaEmitidaRepository>{
	
	@Autowired
	public FacturaEmitidaService(FacturaEmitidaRepository feRepo) {
		super.facturaRepo = feRepo;
	}
}