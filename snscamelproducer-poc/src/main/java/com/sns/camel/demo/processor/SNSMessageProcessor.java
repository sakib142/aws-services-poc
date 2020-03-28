package com.sns.camel.demo.processor;


import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.sns.camel.demo.model.Order;
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


    /**
     * This is an example of using aws sns client to publish message
     * @param order
     * @return
     */
    public Order processSqsMessages(Order order){
        log.info("Inside processor {}",order.toString());
        order.setDescription(order.getDescription()+" Modified! ");
        amazonSNSClient.publish("aws:sns:us-east-2:721635688037:orderTopic", order.toString());
        return order;
    }
}
