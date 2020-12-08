package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PedidoLogisticException extends RuntimeException{
	
	public PedidoLogisticException() {
		super();
	}
	
	public PedidoLogisticException(String message) {
		super(message);
	}
}
