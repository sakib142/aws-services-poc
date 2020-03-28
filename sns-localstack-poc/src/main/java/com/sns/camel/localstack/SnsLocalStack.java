package com.sns.camel.localstack;


import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SnsLocalStack  implements CommandLineRunner {


    @Autowired
    ProducerTemplate producerTemplate;

    public static void main(String [] args){

        SpringApplication.run(SnsLocalStack.class,args);
    }

	@Override
	public void run(String... args) throws Exception {
		producerTemplate.sendBody("direct:start", "{\n" +
				" \"id\": \"954\",\n" +
				" \"name\": \"localstack sqs\",\n" +
				" \"description\": \"localstack sqs desc\",\n" +
				" \"source\": \"OMS2\",\n" +
				" \"target\": \"EMS2\",\n" +
				" \"venue\": \"MKTX\"\n" +
				"}");
	}
}
