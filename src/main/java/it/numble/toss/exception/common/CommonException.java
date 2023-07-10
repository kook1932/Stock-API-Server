package it.numble.toss.exception.common;

import org.springframework.http.HttpStatus;

public class CommonException extends Exception {

	private Constants.ExceptionClass exceptionClass;
	private HttpStatus httpStatus;

	public CommonException(Constants.ExceptionClass exceptionClass, HttpStatus httpStatus, String message) {
		super(message);
		this.exceptionClass = exceptionClass;
		this.httpStatus = httpStatus;
	}

	public int getHttpStatusCode() {
		return httpStatus.value();
	}

	public String getHttpStatusType() {
		return httpStatus.getReasonPhrase();
	}
}
