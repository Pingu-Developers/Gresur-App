package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UnmodifablePedidoException extends RuntimeException{
	
	public UnmodifablePedidoException () {
		super();
	}
	
	public UnmodifablePedidoException (String message) {
		super(message);
	}
}
