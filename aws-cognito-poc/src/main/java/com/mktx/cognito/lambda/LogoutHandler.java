package com.mktx.cognito.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mktx.cognito.model.LogOutDTO;
import com.mktx.cognito.service.CognitoServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;


@Slf4j
@Import(CognitoServices.class)
public class LogoutHandler implements RequestHandler<LogOutDTO, String> {
    @Override
    public String handleRequest(LogOutDTO logOutDTO, Context context) {
        CognitoServices cognitoServices = new CognitoServices();
        return cognitoServices.signOut(logOutDTO);

    }
}
