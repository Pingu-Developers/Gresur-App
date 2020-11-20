package org.springframework.gresur.service.exceptions;

@SuppressWarnings("serial")
public class ClienteDefaulterException extends Exception{

	public ClienteDefaulterException() {
		super();
	}
	public ClienteDefaulterException(String message) {
		super(message);
	}
}
