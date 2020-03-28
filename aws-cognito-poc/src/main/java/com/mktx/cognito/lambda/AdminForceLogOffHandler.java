package com.mktx.cognito.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mktx.cognito.model.CognitoRequest;
import com.mktx.cognito.service.CognitoServices;
import org.springframework.context.annotation.Import;

@Import(CognitoServices.class)
public class AdminForceLogOffHandler implements RequestHandler<CognitoRequest, String> {

    @Override
    public String handleRequest(CognitoRequest cognitoRequest, Context context) {
        CognitoServices cognitoServices = new CognitoServices();
        cognitoServices.forceLogOFF(cognitoRequest.getUsername(), cognitoRequest.getUserPoolId());
        return "Done";
    }
}
