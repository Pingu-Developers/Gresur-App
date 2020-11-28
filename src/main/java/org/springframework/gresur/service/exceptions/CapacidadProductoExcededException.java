package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CapacidadProductoExcededException extends RuntimeException{
	
	public CapacidadProductoExcededException() {
		super();
	}
	
	public CapacidadProductoExcededException(String message) {
		super(message);
	}

}
