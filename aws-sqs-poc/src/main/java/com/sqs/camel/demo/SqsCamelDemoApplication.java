package com.sqs.camel.demo;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class SqsCamelDemoApplication implements CommandLineRunner{

	@Autowired
	ProducerTemplate producerTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SqsCamelDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		producerTemplate.sendBody("direct:start", "{\n" +
				"  \"id\": \"02\",\n" +
				"  \"name\": \"localstack sqs\",\n" +
				"  \"description\": \"localstack sqs desc\",\n" +
				"  \"source\": \"OMS2\",\n" +
				"  \"target\": \"EMS2\",\n" +
				"  \"venue\": \"MKTX\"\n" +
				"}");
	}
}
