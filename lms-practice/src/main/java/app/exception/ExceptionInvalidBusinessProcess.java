package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Business process is incorrect")
public class ExceptionInvalidBusinessProcess extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionInvalidBusinessProcess() {
		super("Wrong business process");
	}

	public ExceptionInvalidBusinessProcess(String exception) {
		super("Wrong business process: " + exception);
	}
}
