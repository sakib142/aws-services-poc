package com.sns.camel.demo.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.camel.demo.model.Order;
import com.sns.camel.demo.processor.SNSMessageProcessor;
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
    }

    /**
     * get orders from SQS and publish to SNS
     */
    protected void publishOrderToSns() {

        from(SQS_SOURCE_QUEUE)
        .routeId("snsMessageReceiver").description("testing aws sns")
        .unmarshal(new JacksonDataFormat(new ObjectMapper(), Order.class))
        .log("${body}")
        .bean(snsMessageProcessor, "publishOrderToSns")
        .marshal(new JacksonDataFormat(new ObjectMapper(), Order.class))
        .log("${body}")
        .to(SNS_TOPIC)
        .end();
    }
    
}
