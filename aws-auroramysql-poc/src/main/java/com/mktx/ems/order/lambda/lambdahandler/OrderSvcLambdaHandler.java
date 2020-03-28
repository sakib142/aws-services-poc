package com.mktx.ems.order.lambda.lambdahandler;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;
import org.springframework.stereotype.Component;

@Component
public class OrderSvcLambdaHandler extends SpringBootRequestHandler<String, String> {

}
