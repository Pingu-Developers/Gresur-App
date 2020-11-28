package org.springframework.gresur.service.exceptions;

@SuppressWarnings("serial")
public class FechaFinNotAfterFechaInicioException extends RuntimeException {
	
	public FechaFinNotAfterFechaInicioException() {
		super();
	}
	
	public FechaFinNotAfterFechaInicioException(String message) {
		super(message);
	}
	
}
