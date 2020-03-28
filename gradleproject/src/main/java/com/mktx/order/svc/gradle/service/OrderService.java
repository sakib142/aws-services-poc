package com.mktx.order.svc.gradle.service;

import com.mktx.order.svc.gradle.common.constants.Headers;
import com.mktx.order.svc.gradle.model.Order;
import com.mktx.order.svc.gradle.repository.OrderRepository;
import io.swagger.annotations.ApiOperation;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @ApiOperation(value = "Find list of active orders ")
    public List<Order> findActiveOrders(@Header(Headers.EMS_FIRM_ID) String firmId) {
        return orderRepository.findByFirmIdAndActiveOrderByCreateTsDesc(firmId, true);
    }


}
