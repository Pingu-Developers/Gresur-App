package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ClienteDefaulterException extends RuntimeException{

	public ClienteDefaulterException() {
		super();
	}
	public ClienteDefaulterException(String message) {
		super(message);
	}
}
