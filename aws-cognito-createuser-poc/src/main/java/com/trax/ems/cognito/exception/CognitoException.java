package com.trax.ems.cognito.exception;

import javax.security.sasl.AuthenticationException;

import lombok.Getter;

public class CognitoException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MODULE_CODE = "cognito";
    public static final String GENERIC_EXCEPTION_CODE = "00";
    public static final String INVALID_PASS_WORD_EXCEPTION = "01";
    public static final String ACCESS_TOKEN_MISSING_EXCEPTION = "02";
    public static final String USER_MUST_CHANGE_PASS_WORD_EXCEPTION_CODE = "03";
    public static final String USER_MUST_DO_ANOTHER_CHALLENGE = "04";
    public static final String INVALID_USERNAME_EXCEPTION = "05";
    public static final String INVALID_ACCESS_TOKEN_EXCEPTION = "06";
    public static final String EMAIL_MISSING_EXCEPTION = "07";
    public static final String NO_TOKEN_PROVIDED_EXCEPTION = "08";
    public static final String INVALID_TOKEN_EXCEPTION_CODE = "09";
    public static final String NOT_A_TOKEN_EXCEPTION = "10";
    public static final String NO_CODE_GIVEN = "11";




    @Getter
    private final String errorCode;
    @Getter
    private final String errorMessage;
    @Getter
    private final String detailErrorMessage;

    public CognitoException(String message, String code, String detail) {
        super(message);
        errorCode = code;
        errorMessage = message;
        detailErrorMessage = detail;
    }

    public static String getErrorCodeByService(String serviceCode, String exceptionCode) {
        String errorCode;

        errorCode = MODULE_CODE + serviceCode + exceptionCode;

        return errorCode;
    }
}
