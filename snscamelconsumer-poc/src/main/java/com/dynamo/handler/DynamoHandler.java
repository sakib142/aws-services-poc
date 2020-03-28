package com.dynamo.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.dynamo.model.Order;
import com.dynamo.service.DynamoService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DynamoHandler implements RequestHandler<SNSEvent, String> {

    public String handleRequest(SNSEvent event, Context context) {

        DynamoService dynamoService = new DynamoService();
        System.out.println("Event is : "+event);
        try {
            for (SNSEvent.SNSRecord record: event.getRecords()) {
                String message = record.getSNS().getMessage();
                System.out.println("Message is : "+ message);
                ObjectMapper mapper = new ObjectMapper();
                Order order = mapper.readValue(message , Order.class);
                dynamoService.createItem(order);
            }
        }catch (Exception e){
            System.err.println("Error getting records from SNS");
            System.err.println(e.getMessage());
        }
        return "Order has been inserted successfully!!";
    }
}
