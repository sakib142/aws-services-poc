package com.mktx.cognito.lambda;

import com.amazonaws.services.cognitoidp.model.AuthEventType;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mktx.cognito.model.CognitoRequest;
import com.mktx.cognito.service.CognitoServices;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(CognitoServices.class)
public class UserLoginHistoryHandler implements RequestHandler<CognitoRequest, List<AuthEventType>> {

    @Override
    public List<AuthEventType> handleRequest(CognitoRequest cognitoRequest, Context context) {
        CognitoServices cognitoServices = new CognitoServices();
        return cognitoServices.findLoginHistory(cognitoRequest.getUsername(), cognitoRequest.getUserPoolId());
    }
}
