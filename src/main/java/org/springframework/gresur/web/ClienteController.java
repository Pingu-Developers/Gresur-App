package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.ClienteService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

	private final ClienteService clienteService;
	
	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
}
