package com.sns.camel.localstack.processor;


import com.amazonaws.services.sns.AmazonSNS;
import com.sns.camel.localstack.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SNSMessageProcessor {

    @Autowired
    AmazonSNS amazonSNSClient;

    public Order publishOrderToSns(Order order){
        order.setDescription(order.getDescription()+" Modified! ");
        log.info("Inside SNS processor {}",order.toString());
        return order;
    }

}
