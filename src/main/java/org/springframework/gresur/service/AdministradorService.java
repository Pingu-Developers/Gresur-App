package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.repository.AdministradorRepository;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService extends PersonalService<Administrador, AdministradorRepository>{
		
	@Autowired
	public AdministradorService(AdministradorRepository admRepository) {
		super.personalRepo = admRepository;
	}	
}
