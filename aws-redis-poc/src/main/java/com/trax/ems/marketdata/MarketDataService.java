package com.trax.ems.marketdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class MarketDataService {

	public static void main(String[] args) {
		SpringApplication.run(MarketDataService.class, args);
	}

}

