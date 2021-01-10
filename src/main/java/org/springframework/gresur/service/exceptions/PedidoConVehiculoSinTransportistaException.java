package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PedidoConVehiculoSinTransportistaException extends RuntimeException{
	
	public PedidoConVehiculoSinTransportistaException() {
		super();
	}

	public PedidoConVehiculoSinTransportistaException(String message) {
		super(message);
	}
}
