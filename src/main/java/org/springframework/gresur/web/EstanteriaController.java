package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.EstanteriaService;

public class EstanteriaController {
	private final EstanteriaService estanteriaService;
	
	@Autowired
	public EstanteriaController(EstanteriaService estanteriaService) {
		this.estanteriaService = estanteriaService;
	}
	
	

}
