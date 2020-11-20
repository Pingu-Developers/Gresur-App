package org.springframework.gresur.service.exceptions;

@SuppressWarnings("serial")
public class NotificacionesLimitException extends Exception {
	
	public NotificacionesLimitException() {
		super();
	}

	public NotificacionesLimitException(String message) {
		super(message);
	}
}
