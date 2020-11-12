package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.TransportistaService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransportistaController {
	
	private final TransportistaService transportistaService;

	@Autowired
	public TransportistaController(TransportistaService transportistaService) {
		this.transportistaService = transportistaService;
	}

}
