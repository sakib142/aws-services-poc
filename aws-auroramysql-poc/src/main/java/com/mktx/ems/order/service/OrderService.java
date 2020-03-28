package com.mktx.ems.order.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mktx.ems.order.common.constants.Headers;
import com.mktx.ems.order.model.Order;
import com.mktx.ems.order.repository.OrderRepository;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@ApiOperation(value = "Find list of active orders ")
	public List<Order> findActiveOrders(String firmId) {
		return orderRepository.findByFirmIdAndActiveOrderByCreateTsDesc(firmId, true);
	}

	@ApiOperation(value = "Insert Order ")
	public void insertOrders() {
		log.info("Order insertOrders called");
		Order order = new Order();
		order.setId(BigInteger.probablePrime(17, new Random()));
		order.setSettlCurrency("USD");
		order.setActive(true);
		order.setCapacity("20");
		order.setClientId("100");
		order.setClientOrderId("200");
		order.setCusip("1234");
		order.setIsin("1234");
		order.setOrderType("B");
		order.setFirmId("111");

		orderRepository.save(order);
		log.info("Order Inserted");

	}

}
