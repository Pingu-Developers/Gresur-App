package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class VehiculoNotAvailableException extends RuntimeException{

	public VehiculoNotAvailableException() {
		super();
	}

	public VehiculoNotAvailableException(String message) {
		super(message);
	}
}
