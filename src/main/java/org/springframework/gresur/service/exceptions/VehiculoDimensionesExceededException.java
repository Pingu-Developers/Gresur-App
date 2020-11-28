package org.springframework.gresur.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class VehiculoDimensionesExceededException extends RuntimeException {
	public VehiculoDimensionesExceededException() {
		super();
	}

	public VehiculoDimensionesExceededException(String message) {
		super(message);
	}
}
