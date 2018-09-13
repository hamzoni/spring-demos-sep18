package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Incorrect parameters key or value")
public class ExceptionMalformParam extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6512913631402660721L;

	public ExceptionMalformParam() {
		super("Request body is malformatted");
	}

	public ExceptionMalformParam(String exception) {
		super("Request body is malformatted: " + exception);
	}
}
