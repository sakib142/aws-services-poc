package com.mktx.cognito.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mktx.cognito.model.CognitoRequest;
import com.mktx.cognito.service.CognitoServices;
import net.minidev.json.JSONObject;
import org.springframework.context.annotation.Import;

@Import(CognitoServices.class)
public class TokenVerifyHandler implements RequestHandler<CognitoRequest, JSONObject> {

    @Override
    public JSONObject handleRequest(CognitoRequest cognitoRequest, Context context) {
        CognitoServices cognitoServices = new CognitoServices();
        try {
            return cognitoServices.verifyTheToken(cognitoRequest.getAccessToken() == null ? cognitoRequest.getIdToken() : cognitoRequest.getAccessToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
