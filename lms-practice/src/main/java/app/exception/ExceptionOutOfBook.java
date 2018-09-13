package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.GONE, reason="The request is unable to be fullfilled")
public class ExceptionOutOfBook extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionOutOfBook() {
		super("Intended books is out of stock");
	}

	public ExceptionOutOfBook(String exception) {
		super("The intended book is out of stock: " + exception);
	}
}
