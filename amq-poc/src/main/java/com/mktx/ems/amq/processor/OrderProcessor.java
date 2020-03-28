package com.mktx.ems.amq.processor;


import com.mktx.ems.amq.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderProcessor {


    public Order processOrder(Order order){
        log.info("Inside processor {}",order.toString());
        order.setDescription(order.getDescription()+" Modified! ");
        return order;
    }
}
