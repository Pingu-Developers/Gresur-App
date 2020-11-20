package org.springframework.gresur.service.exceptions;

@SuppressWarnings("serial")
public class StockWithoutEstanteriaException extends Exception{
	
	public StockWithoutEstanteriaException() {
		super();
	}
	
	public StockWithoutEstanteriaException(String message) {
		super(message);
	}


}
