package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PedidoEntregadoCanceladoException extends RuntimeException{
	
	public PedidoEntregadoCanceladoException () {
		super();
	}
	
	public PedidoEntregadoCanceladoException (String message) {
		super(message);
	}
}
