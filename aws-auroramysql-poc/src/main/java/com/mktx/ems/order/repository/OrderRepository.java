package com.mktx.ems.order.repository;

import com.mktx.ems.order.model.Order;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends CrudRepository<Order, BigInteger> {

    List<Order> findByFirmIdAndActiveOrderByCreateTsDesc(String firmId, boolean active);



}
