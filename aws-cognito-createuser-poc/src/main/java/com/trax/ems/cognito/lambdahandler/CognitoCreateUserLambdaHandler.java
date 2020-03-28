package com.trax.ems.cognito.lambdahandler;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

@Component
public class CognitoCreateUserLambdaHandler	extends SpringBootRequestHandler<DynamodbEvent, String> {

}
