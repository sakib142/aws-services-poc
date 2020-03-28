package com.sqs.camel.demo.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqs.camel.demo.model.Order;
import com.sqs.camel.demo.processor.SQSMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SQSRouteBuilder extends SpringRouteBuilder {


    @Value("${aws.sqs.sourcequeue.url}")
    private String AWS_SOURCE_QUEUE;

    @Value("${aws.sqs.targetqueue.url}")
    private String AWS_TARGET_QUEUE;

    @Value("${localstack.sqs.sourcequeue.url}")
    private String LS_SOURCE_QUEUE;

    @Value("${localstack.sqs.targetqueue.url}")
    private String LS_TARGET_QUEUE;

    @Autowired
    SQSMessageProcessor sqsMessageProcessor;

    @Override
    public void configure() throws Exception {
        //Aws Queue Operations
        makeAwsQueueRoute();

        //Localstack Queue Operations
        sendMessageRoute();
        processMessageRoute();
        getMessageRoute();
    }

    protected void makeAwsQueueRoute() {
         from(AWS_SOURCE_QUEUE)
        .routeId("awsReceiver").description("Testing aws sqs")
        .unmarshal(new JacksonDataFormat(new ObjectMapper(), Order.class))
        .log("${body}")
        .bean(sqsMessageProcessor, "processSqsMessages")
        .to(AWS_TARGET_QUEUE)
        .end();
    }

    protected void sendMessageRoute() {
        from("direct:start")
        .routeId("sqsMessageSender").description("Testing aws localstack sqs")
        .log("${body}")
        .to(LS_SOURCE_QUEUE)
        .end();
    }

    protected void processMessageRoute() {
        from(LS_SOURCE_QUEUE)
        .routeId("sqsMessageProcessor").description("Testing aws localstack sqs")
        .unmarshal(new JacksonDataFormat(new ObjectMapper(), Order.class))
        .log("${body}")
        .bean(sqsMessageProcessor, "processSqsMessages")
        .to(LS_TARGET_QUEUE)
        .end();
    }

    protected void getMessageRoute() {
        from(LS_TARGET_QUEUE)
        .routeId("sqsMessageReceiver").description("Testing aws localstack sqs")
        .log("${body}")
        .end();
    }
    
}
