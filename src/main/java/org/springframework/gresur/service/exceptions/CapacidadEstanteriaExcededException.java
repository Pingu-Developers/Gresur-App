package org.springframework.gresur.service.exceptions;

@SuppressWarnings("serial")
public class CapacidadEstanteriaExcededException extends Exception{

	public CapacidadEstanteriaExcededException() {
		super();
	}
	
	public CapacidadEstanteriaExcededException(String message) {
		super(message);
	}
}
