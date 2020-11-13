package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.ConfiguracionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfiguracionController {
		
	private final ConfiguracionService configService;
		
	@Autowired
	public ConfiguracionController(ConfiguracionService configService) {
		this.configService = configService;
	}
}
