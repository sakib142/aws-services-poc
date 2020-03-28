package com.mktx.ems.order.lambda;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mktx.ems.order.model.Order;
import com.mktx.ems.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Component("OrderSvcLambdaHandler")
@Slf4j
public class OrderSvcLambda implements Function<String, String> {

	@Autowired
	OrderService orderService;

	@Override
	public String apply(String t) {
		log.info("Lambda Invoked");
		if (t.equalsIgnoreCase("insert"))
			orderService.insertOrders();
		if (t.equalsIgnoreCase("search")) {
			List<Order> order = orderService.findActiveOrders("111");
			order.forEach(System.out::println);
		}
		log.info("Lambda invocation completed");
		return "success";
	}

}
