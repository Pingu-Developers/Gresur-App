package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MMAExceededException extends RuntimeException{	

	public MMAExceededException() {
		super();
	}

	public MMAExceededException(String message) {
		super(message);
	}
}
