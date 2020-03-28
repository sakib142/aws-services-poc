package com.trax.ems.marketdepth.repository;

import java.math.BigInteger;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.trax.ems.marketdepth.model.MarketDepth;

@EnableScan
public interface MarketDepthRepository extends CrudRepository<MarketDepth, String> {


	Iterable<MarketDepth> findAllByInstId(BigInteger instId);

	Iterable<MarketDepth> findAllByCurrency(String currency);

	Iterable<MarketDepth> findAll();


}