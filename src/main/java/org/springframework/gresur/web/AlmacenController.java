package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlmacenController {
	
	private AlmacenService almacenService;
	
	@Autowired
	public AlmacenController(AlmacenService almacenService) {
		this.almacenService = almacenService;
	}
	
	
	

}
