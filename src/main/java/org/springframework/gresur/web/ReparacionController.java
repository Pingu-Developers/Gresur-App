package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.ReparacionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReparacionController {
	
	private final ReparacionService reparacionService;
	
	@Autowired
	public ReparacionController(ReparacionService reparacionService) {
		this.reparacionService = reparacionService;
	}

}
