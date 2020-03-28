package com.sqs.camel.demo.processor;


import com.sqs.camel.demo.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SQSMessageProcessor {


    public Order processSqsMessages(Order order){
        log.info("Inside processor {}",order.toString());
        order.setDescription(order.getDescription()+" Modified! ");
        return order;
    }
}
