package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="The item is not found in system")
public class ExceptionRecordNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExceptionRecordNotFound() {
		super("Cannot find item");
	}

	public ExceptionRecordNotFound(String exception) {
		super("Cannot find item: " + exception);
	}
}
