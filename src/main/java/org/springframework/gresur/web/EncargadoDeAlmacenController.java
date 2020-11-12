package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.EncargadoDeAlmacenService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EncargadoDeAlmacenController {
	
	private final EncargadoDeAlmacenService encargadoService;
	
	@Autowired
	public EncargadoDeAlmacenController(EncargadoDeAlmacenService encargadoService) {
		this.encargadoService = encargadoService;
	}
	
	

}
