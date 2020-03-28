package com.mktx.lambdahandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mktx.model.Order;
import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Data
public class OrderLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {

        System.out.println("request : "+ request);

        System.out.println("Headers : "+request.getHeaders());

        Map<String, Object> authorizer = request.getRequestContext().getAuthorizer();

        System.out.println("authorizer map : "+request.getRequestContext().getAuthorizer());

        System.out.println("UserName : "+authorizer.get("username"));

        List<Order> orders = new ArrayList<Order>();

        System.out.println("Getting orders..");
        Order order1 = new Order();
        order1.setId(1l);
        order1.setName("test order");
        order1.setDescription("test order desc");
        order1.setSource("oms");
        order1.setTarget("ems");
        order1.setVenue("mktx");

        Order order2 = new Order();
        order2.setId(2l);
        order2.setName("test order2");
        order2.setDescription("test order desc2");
        order2.setSource("oms2");
        order2.setTarget("ems2");
        order2.setVenue("mktx2");

        orders.add(order1);
        orders.add(order2);
        String body = null;
        try {
            body = new Gson().toJson(orders);
        }catch (Exception e){
            System.out.println("Exception caught"+e);
        }

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        response.setIsBase64Encoded(false);
        response.setStatusCode(200);
        response.setBody(body);


        return response;
    }
}
