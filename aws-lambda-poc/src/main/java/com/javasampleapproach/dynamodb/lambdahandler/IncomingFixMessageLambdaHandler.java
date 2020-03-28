package com.javasampleapproach.dynamodb.lambdahandler;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;


public class IncomingFixMessageLambdaHandler extends SpringBootRequestHandler<DynamodbEvent, String> {

}
