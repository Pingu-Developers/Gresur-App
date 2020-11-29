package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FechaFinNotAfterFechaInicioException extends RuntimeException {
	
	public FechaFinNotAfterFechaInicioException() {
		super();
	}
	
	public FechaFinNotAfterFechaInicioException(String message) {
		super(message);
	}
	
}
