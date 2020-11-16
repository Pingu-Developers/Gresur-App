package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.repository.DependienteRepository;
import org.springframework.stereotype.Service;

@Service
public class DependienteService extends PersonalService<Dependiente, DependienteRepository>{
	
	@Autowired
	public DependienteService(DependienteRepository dependienteRepository) {
		super.personalRepo = dependienteRepository;
	}	
}
