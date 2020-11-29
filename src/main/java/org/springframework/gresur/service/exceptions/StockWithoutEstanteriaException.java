package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class StockWithoutEstanteriaException extends RuntimeException{
	
	public StockWithoutEstanteriaException() {
		super();
	}
	
	public StockWithoutEstanteriaException(String message) {
		super(message);
	}


}
