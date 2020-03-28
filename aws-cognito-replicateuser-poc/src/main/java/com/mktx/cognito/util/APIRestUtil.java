package com.mktx.cognito.util;

import org.springframework.http.HttpStatus;

public class APIRestUtil {
    public static boolean isHTTPError(HttpStatus statusCode) {
        return statusCode.is4xxClientError() || statusCode.is5xxServerError();
    }
}
