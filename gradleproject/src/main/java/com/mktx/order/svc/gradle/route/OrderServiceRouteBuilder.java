package com.mktx.order.svc.gradle.route;

import com.mktx.order.svc.gradle.configuration.EmsCacheManager;
import com.mktx.order.svc.gradle.service.OrderService;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceRouteBuilder extends SpringRouteBuilder {

    @Autowired
    private OrderService orderService;


    @Autowired
    private EmsCacheManager cacheManager;


    public void configure() {

        addGetActiveOrdersRoute();
        addSwaggerConfig();
        addRestletConfig();
    }


    protected void addGetActiveOrdersRoute() {

        rest()
                .path("/order").description("Order Service API")
                .consumes("application/json")
                .get()
                .type(String.class)
                .to("direct:GetActiveOrders").description("Redirect to fetch Active Orders");

        from("direct:GetActiveOrders")
                .log("${body}")
                .bean(cacheManager, "cacheFirmID").description("Method call cached Firm ID")
		.bean(orderService, "findActiveOrders").description("Method call for active orders")

                .log("${body}")
                .end();
    }

    private void addSwaggerConfig() {
        /**
         * Swagger Configuration
         */
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                .port("{{camelrestlet.server.port}}")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "EMS Order API")
                .apiProperty("api.version", "1.0.0");

    }

    private void addRestletConfig() {
        /**
         * Used 'restlet', which is a component for providing REST services.
         */
        restConfiguration()
                .component("restlet")
                .host("0.0.0.0")
                .port("{{camelrestlet.server.port}}")
                .bindingMode(RestBindingMode.auto);
    }

}
