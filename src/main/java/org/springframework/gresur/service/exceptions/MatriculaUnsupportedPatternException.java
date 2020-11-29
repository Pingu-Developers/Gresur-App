package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MatriculaUnsupportedPatternException extends RuntimeException{

	public MatriculaUnsupportedPatternException () {
		super();
	}
	
	public MatriculaUnsupportedPatternException (String message) {
		super(message);
	}
		
}
