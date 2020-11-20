package org.springframework.gresur.service.exceptions;

@SuppressWarnings("serial")
public class CapacidadProductoExcededException extends Exception{
	
	public CapacidadProductoExcededException() {
		super();
	}
	
	public CapacidadProductoExcededException(String message) {
		super(message);
	}

}
