package com.trax.ems.marketdepth;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.trax.ems.marketdepth.lambdahandler.MarketDepthlambda;
import com.trax.ems.marketdepth.service.MarketDepthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class MarketDepthApplication {

	@Autowired
	private MarketDepthService marketDepthService;

	@Autowired
	private MarketDepthlambda marketDepthlambda;

	public static void main(String[] args) {
		SpringApplication.run(MarketDepthApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(AmazonDynamoDB amazonDynamoDB){

		return args -> {

			log.info("Started table creation on Localstack::");
			amazonDynamoDB.listTables().getTableNames().forEach(log::info);
			//Note: you need to comment below line once the table is created after first execution
			marketDepthService.createTable("MarketDepthTb");
			log.info("Finished table creation on Localstack::");
			amazonDynamoDB.listTables().getTableNames().forEach(log::info);
			log.info("Run operations against Localstack");
			marketDepthlambda.apply(null);
			log.info("Finished Localstack operations.");
		};
	}
}
