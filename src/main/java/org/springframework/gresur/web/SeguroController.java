package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.SeguroService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeguroController {

	private final SeguroService seguroService;
	
	@Autowired
	public SeguroController(SeguroService seguroService) {
		this.seguroService = seguroService;
	}
	
	
}
