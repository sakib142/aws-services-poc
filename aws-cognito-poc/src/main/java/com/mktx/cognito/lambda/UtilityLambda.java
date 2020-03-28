package com.mktx.cognito.lambda;

import com.amazonaws.services.cognitoidp.model.UserType;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mktx.cognito.service.CognitoServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

import java.util.List;


@Slf4j
@Import(CognitoServices.class)
public class UtilityLambda implements RequestHandler<String, List<UserType>> {
    @Override
    public List<UserType> handleRequest(String userPoolId, Context context) {
        CognitoServices cognitoServices = new CognitoServices();
        return cognitoServices.numberofAuthenticatedUser(userPoolId);

    }
}
