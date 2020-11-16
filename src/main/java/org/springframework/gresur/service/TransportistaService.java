package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.repository.TransportistaRepository;
import org.springframework.stereotype.Service;

@Service
public class TransportistaService extends PersonalService<Transportista, TransportistaRepository>{
	
	@Autowired
	public TransportistaService(TransportistaRepository transportistaRepo) {
		super.personalGRepo = transportistaRepo;
	}		
}
