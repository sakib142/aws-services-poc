package com.mktx.rds.repository;


import com.mktx.rds.model.Order;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Order> findAll();

}
