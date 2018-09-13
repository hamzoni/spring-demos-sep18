package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Duplicated data field. Unique constraint violation.")
public class ExceptionDuplicateRecord extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionDuplicateRecord() {
		super("Record data field should not be duplicated");
	}

	public ExceptionDuplicateRecord(String exception) {
		super("Record data field should not be duplicated: " + exception);
	}
}
