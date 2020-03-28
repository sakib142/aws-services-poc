package com.sns.camel.localstack.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.camel.localstack.model.Order;
import com.sns.camel.localstack.processor.SNSMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SNSRouteBuilder extends SpringRouteBuilder {

    @Autowired
    SNSMessageProcessor snsMessageProcessor;

    @Value("${aws.sqs.queue.url}")
    private String SQS_SOURCE_QUEUE;

    @Value("${aws.sns.url}")
    private String SNS_TOPIC;


    @Override
    public void configure() throws Exception {
        publishOrderToSns();
        receiveOrderFromSns();
    }

    /**
     * publish orders to SNS
     */
    protected void publishOrderToSns() {

        from("direct:start")
        .routeId("snsMessagePublisher").description("testing aws local stack sns")
        .log("${body}")
        .unmarshal(new JacksonDataFormat(new ObjectMapper(), Order.class))
        .bean(snsMessageProcessor, "publishOrderToSns")
        .to(SNS_TOPIC)
        .end();
    }

    protected void receiveOrderFromSns() {

        from(SQS_SOURCE_QUEUE)
        .routeId("snsMessageReceiver").description("testing aws local stack sns")
        .log("${body}")
        .end();
    }


    
}
