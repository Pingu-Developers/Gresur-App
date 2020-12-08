package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PedidoNoDeleteableException extends RuntimeException{
	
	public PedidoNoDeleteableException() {
		super();
	}
	
	public PedidoNoDeleteableException(String message) {
		super(message);
	}
}
