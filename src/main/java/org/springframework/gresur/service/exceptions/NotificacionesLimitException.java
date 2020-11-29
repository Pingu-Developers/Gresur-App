package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotificacionesLimitException extends RuntimeException{
	
	public NotificacionesLimitException() {
		super();
	}

	public NotificacionesLimitException(String message) {
		super(message);
	}
}
