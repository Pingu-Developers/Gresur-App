package org.springframework.gresur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.EncargadoDeAlmacen;
import org.springframework.gresur.repository.EncargadoDeAlmacenRepository;
import org.springframework.stereotype.Service;

@Service
public class EncargadoDeAlmacenService extends PersonalService<EncargadoDeAlmacen, EncargadoDeAlmacenRepository>{
		
	@Autowired
	public EncargadoDeAlmacenService(EncargadoDeAlmacenRepository encargadoDeAlmacenRepository) {
		super.personalGRepo = encargadoDeAlmacenRepository;
	}
}
