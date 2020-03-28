package com.mktx.cognito.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mktx.cognito.model.CodeDTO;
import com.mktx.cognito.service.CognitoServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;


@Slf4j
@Import(CognitoServices.class)
public class LoggedInUserHandler implements RequestHandler<CodeDTO, Boolean> {
    @Override
    public Boolean handleRequest(CodeDTO codeDTO, Context context) {
        CognitoServices cognitoServices= new CognitoServices();
        return cognitoServices.isLoggedIn(codeDTO);
    }
}
