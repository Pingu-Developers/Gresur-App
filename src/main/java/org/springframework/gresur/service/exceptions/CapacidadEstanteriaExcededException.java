package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CapacidadEstanteriaExcededException extends RuntimeException{

	public CapacidadEstanteriaExcededException() {
		super();
	}
	
	public CapacidadEstanteriaExcededException(String message) {
		super(message);
	}
}
