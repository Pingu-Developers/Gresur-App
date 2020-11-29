package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class VehiculoIllegalException extends RuntimeException{

	public VehiculoIllegalException () {
		super();
	}
	
	public VehiculoIllegalException (String message) {
		super(message);
	}
		
}