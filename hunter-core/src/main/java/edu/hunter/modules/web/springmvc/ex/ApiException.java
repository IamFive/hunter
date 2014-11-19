package edu.hunter.modules.web.springmvc.ex;

import java.text.*;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApiException() {
		super();
	}

	public ApiException(String message) {
		super(message);
	}

	public ApiException(String message, Object... formatArgs) {
		super(MessageFormat.format(message, formatArgs));
	}

	public ApiException(Throwable cause) {
		super(cause);
	}

	public ApiException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String toString() {
		return "ApiException [getMessage()=" + getMessage() + "]";
	}

}
