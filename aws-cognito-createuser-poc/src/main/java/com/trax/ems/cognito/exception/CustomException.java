package com.trax.ems.cognito.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CustomException(HttpStatus httpStatus, String errorReason, String message) {
        super(errorReason + message);
    }
}
