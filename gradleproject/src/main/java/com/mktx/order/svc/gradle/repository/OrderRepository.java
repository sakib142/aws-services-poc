package com.mktx.order.svc.gradle.repository;

import com.mktx.order.svc.gradle.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;


@Repository
public interface OrderRepository extends CrudRepository<Order, BigInteger> {

	List<Order> findByFirmIdAndActiveOrderByCreateTsDesc(String firmId, boolean active);



}