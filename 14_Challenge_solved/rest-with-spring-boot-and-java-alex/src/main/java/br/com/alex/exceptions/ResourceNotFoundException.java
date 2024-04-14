package br.com.alex.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 8254966347357340877L;
	public ResourceNotFoundException(String ex) {
		super(ex);
	}

	
}
