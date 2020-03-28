package com.mktx.cognito.lambda;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;
import org.springframework.stereotype.Component;

import com.mktx.cognito.model.CognitoRequest;
import com.mktx.cognito.model.CognitoUser;

@Component
public class CognitoReplicateUserLambdaHandler extends SpringBootRequestHandler<CognitoRequest, CognitoUser> {

}
