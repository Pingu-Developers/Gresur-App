package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.repository.TransportistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransportistaService extends PersonalService<Transportista, TransportistaRepository>{
	
	@Autowired
	public TransportistaService(TransportistaRepository transportistaRepo) {
		super.personalRepo = transportistaRepo;
	}
	@Transactional(readOnly = true)
	public Long findIdTransportistaConMenosPedidos(){
		return super.personalRepo.findIdTransportistaConMenosPedidos().orElse(null);
	}
	@Transactional(readOnly = true)
	public Transportista findTransportistaConMenosPedidos(){
		return super.personalRepo.findById(this.findIdTransportistaConMenosPedidos()).orElse(null);
	}

}
