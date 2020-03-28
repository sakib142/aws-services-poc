package com.mktx.rds.controller;

import com.mktx.rds.model.Order;
import com.mktx.rds.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="/order")
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping(value="/createOrder")
    public void createOrder(@ModelAttribute("order") Order order){
        orderService.createOrder(order);
    }

    @GetMapping(value="/id")
    @ResponseBody
    public Order getOrder(@RequestParam("orderId") Long orderId){
        Order order = orderService.findOrderById(orderId);
        return order;
    }

    @GetMapping(value="/getOrders")
    @ResponseBody
    public List<Order> getOrders(){
        List<Order> orders = orderService.findAll();
        return orders;
    }

    @DeleteMapping(value="/id")
    public void deleteOrder(@RequestParam("orderId") Long orderId){
        orderService.deleteOrder(orderId);
    }

    @PutMapping(value="/updateOrder")
    @ResponseBody
    public Order updateOrder(@ModelAttribute("order") Order order){
        return orderService.updateOrder(order);
    }

}
