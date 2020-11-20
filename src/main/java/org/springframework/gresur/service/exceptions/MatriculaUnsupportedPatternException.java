package org.springframework.gresur.service.exceptions;

@SuppressWarnings("serial")
public class MatriculaUnsupportedPatternException extends Exception{

	public MatriculaUnsupportedPatternException () {
		super();
	}
	
	public MatriculaUnsupportedPatternException (String message) {
		super(message);
	}
		
}
