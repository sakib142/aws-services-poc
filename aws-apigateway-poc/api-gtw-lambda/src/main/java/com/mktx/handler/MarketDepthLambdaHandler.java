package com.mktx.handler;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.LinkedHashMap;

public class MarketDepthLambdaHandler
        extends SpringBootRequestHandler<LinkedHashMap, APIGatewayProxyResponseEvent> {

}
