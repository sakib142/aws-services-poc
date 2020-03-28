package com.mktx.ems.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mktx.ems.order.common.config.EMSBeanConfiguration;

@SpringBootApplication
/*
 * @EnableJpaRepositories(basePackages = "com.mktx.ems.order.repository")
 * 
 * @EntityScan({"com.mktx.ems.order.model"})
 * 
 * @Import(EMSBeanConfiguration.class)
 * 
 * @EnableCaching
 */
public class StartOrderService {

	public static void main(String[] args) {
		SpringApplication.run(StartOrderService.class, args);
	}

}
