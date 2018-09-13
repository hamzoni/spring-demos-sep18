package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Request's param(s) is not valid")
public class ExceptionInvalidParam extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionInvalidParam() {
		super("Invalid parameter");
	}

	public ExceptionInvalidParam(String exception) {
		super("Invalid parameter: " + exception);
	}
}
