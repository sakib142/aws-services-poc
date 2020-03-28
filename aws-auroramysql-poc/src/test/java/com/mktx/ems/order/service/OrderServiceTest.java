package com.mktx.ems.order.service;

import com.mktx.ems.order.model.Order;
import com.mktx.ems.order.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

   @Mock
    private OrderRepository orderRepository;


    @InjectMocks
    private OrderService orderService;

    @Mock
    Order order;
    

    
    @Mock
    List<Order> orderList;

   
    @Test
	public void testMarketDepthWithFirmId() {
		when(orderRepository.findByFirmIdAndActiveOrderByCreateTsDesc("5874", true)).thenReturn(orderList);
		List<Order> orders = orderService.findActiveOrders("5874");
		assertEquals(orders.size(), orderList.size());
	}
    

}

