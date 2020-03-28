package com.mktx.ems.amq.route;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mktx.ems.amq.model.Order;
import com.mktx.ems.amq.processor.OrderProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class OrderRouteBuilder extends SpringRouteBuilder {


    @Value("${order.sourcequeue.url}")
    private String SOURCE_QUEUE;

    @Value("${order.topic.url}")
    private String EMS_ORDER_TOPIC;

    @Value("${order.targetqueue.url}")
    private String TARGET_QUEUE;


    @Autowired
    OrderProcessor orderProcessor;

    @Override
    public void configure() throws Exception {
        makeOrderRoute();
        makeOrderTopicRoute();
        makeOrderTargetQueueRoute();
    }

    protected void makeOrderRoute() {

        from(SOURCE_QUEUE)
        .routeId("SourceQueueRoute").description("Testing ems order source queue")
        .unmarshal(new JacksonDataFormat(new ObjectMapper(), Order.class))
        .log("${body}")
        .bean(orderProcessor, "processOrder")
        .marshal(new JacksonDataFormat(new ObjectMapper(), Order.class))
        .to(EMS_ORDER_TOPIC)
        .end();
    }

    protected void makeOrderTopicRoute() {

        from(EMS_ORDER_TOPIC)
        .routeId("TopicRoute").description("Testing ems order topic")
        .log("${body}")
        .unmarshal(new JacksonDataFormat(new ObjectMapper(), Order.class))
        .to(TARGET_QUEUE)
        .end();
    }

    protected void makeOrderTargetQueueRoute() {

        from(TARGET_QUEUE)
        .routeId("TargetQueueRoute").description("Testing ems target queue")
        .marshal(new JacksonDataFormat(new ObjectMapper(), Order.class))
        .log("${body}")
        .end();
    }
    
}
