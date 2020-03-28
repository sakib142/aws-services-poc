package com.mktx.rds.lambdahandler;

import com.mktx.rds.model.Order;
import com.mktx.rds.model.RequestDTO;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

public class OrderLambdaHandler extends SpringBootRequestHandler<RequestDTO, String> {
}
