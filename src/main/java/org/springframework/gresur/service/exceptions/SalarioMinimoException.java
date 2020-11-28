package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SalarioMinimoException extends RuntimeException{
	public SalarioMinimoException() {
		super();
	}
	
	public SalarioMinimoException(String message) {
		super(message);
	}
}
