package com.mktx.cognito.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mktx.cognito.model.CognitoRequest;
import com.mktx.cognito.model.CoginitoResponse;
import com.mktx.cognito.service.CognitoServices;
import org.springframework.context.annotation.Import;

@Import(CognitoServices.class)
public class CognitoHandler implements RequestHandler<CognitoRequest, CoginitoResponse> {


    @Override
    public CoginitoResponse handleRequest(CognitoRequest cognitoRequest, Context context) {
        CognitoServices cognitoServices = new CognitoServices();
        return cognitoServices.signUp(cognitoRequest);
    }
}
