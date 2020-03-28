package com.mktx.ems.lambdaauth.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {
    private HttpStatus httpStatus;

    public CustomException(HttpStatus httpStatus, String errorReason, String message) {
        super(errorReason + message);
    }
}
