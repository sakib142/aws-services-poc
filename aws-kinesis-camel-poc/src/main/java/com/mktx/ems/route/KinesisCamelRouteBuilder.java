package com.mktx.ems.route;

import com.mktx.ems.processor.KinesisCamelProcessor;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KinesisCamelRouteBuilder extends SpringRouteBuilder {

    @Autowired
    private KinesisCamelProcessor kinesisCamelProcessor;


    @Override
    public void configure() throws Exception {
        makeKinesisCamelRoute();
//        makeKinesisCamelRoute1();
//        makeKinesisCamelRoute1();
    }

    protected void makeKinesisCamelRoute() {
        from("aws-kcl2://camel-kcl2-test?region=us-east-2&fanout=true")//&protocol=HTTP2")//&metricsLevel=DETAILED&applicationName=camel-kcl2-test1&iteratorType=LATEST")
                .routeId("kinesis-camel-route")//.bean(kinesisCamelProcessor, "process")
                .log("${body}");
//                .to("aws-kinesis://camel-kcl2-test_producer?region=us-east-2");
    }

    protected void makeKinesisCamelRoute1() {
        from("aws-kcl2://camel-kcl2-test?region=us-east-2&applicationName=camel-kcl2-test1")//&applicationName=camel-kcl2-test1&iteratorType=LATEST")
                .routeId("kinesis-camel-route1")//.bean(kinesisCamelProcessor, "process")
                .log("${body}");
//                .to("aws-kcl2://camel-kcl2-test?region=us-east-2");
    }

    protected void makeKinesisCamelRoute2() {
        from("aws-kcl2://camel-kcl2-test_producer?region=us-east-2").routeId("kinesis-camel-route1").log("${body}");
    }

}
