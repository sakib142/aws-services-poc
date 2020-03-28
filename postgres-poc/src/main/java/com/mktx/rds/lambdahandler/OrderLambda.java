package com.mktx.rds.lambdahandler;

import com.mktx.rds.model.EmsInstrument;
import com.mktx.rds.model.Order;
import com.mktx.rds.model.RequestDTO;
import com.mktx.rds.service.InstrumentService;
import com.mktx.rds.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class OrderLambda implements Function<RequestDTO,String> {

    @Autowired
    OrderService orderService;

    @Autowired
    InstrumentService instService;

    @Override
    public String apply(RequestDTO requestDTO) {
        List<EmsInstrument> instruments ;
        List<Order> orders ;


        if (requestDTO.getAction().equalsIgnoreCase("instrument")){
            System.out.println("Received order : " +requestDTO.getEmsInstrument());
            instruments = instService.findAll();
            System.out.println("instruments details fetched successfully!" + instruments);
//            return instruments;

        } else if(requestDTO.getAction().equalsIgnoreCase("order")){
            System.out.println("Autowired orderService : "+orderService);
            System.out.println("Received order : "+requestDTO.getOrder());
            orderService.createOrder(requestDTO.getOrder());
            System.out.println("Order created successfully!");
             orders = orderService.findAll();
            System.out.println("Orders fetched successfully!" + orders);
//            return orders;
        }

        return "Successfully Fetch !";
    }

}
