package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.ITVService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ITVController {

	private final ITVService itvService;

	@Autowired
	public ITVController(ITVService itvService) {
		this.itvService = itvService;
	}
	
	
}
