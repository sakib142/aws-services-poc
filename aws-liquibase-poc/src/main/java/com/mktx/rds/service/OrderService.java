package com.mktx.rds.service;


import com.mktx.rds.model.Order;
import com.mktx.rds.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void saveAll(List<Order> orders){
        orderRepository.saveAll(orders);
    }

    public void createOrder(Order order){
        orderRepository.save(order);
    }


    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Order findOrderById(Long id){
        Order order = orderRepository.findById(id).get();
        System.out.println("got the order : "+order);
        return order;
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

    public Order updateOrder(Order order){
        Order updatedOrder = orderRepository.save(order);
        return updatedOrder;
    }
}
