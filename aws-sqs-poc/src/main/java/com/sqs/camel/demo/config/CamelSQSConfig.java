package com.sqs.camel.demo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CamelSQSConfig {


    @Value("${aws.sqs.accessKey}")
    private String accessKey;
    @Value("${aws.sqs.secretKey}")
    private String secretKey;


    @Bean(name = "awsSqsClient")
    public AmazonSQS awsSqsClient(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonSQS amazonSQSClient = AmazonSQSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(Regions.US_EAST_2).build();
        return amazonSQSClient;
    }

    @Bean(name = "localStackSqsClient")
    public AmazonSQS localStackSqsClient(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("http://10.8.21.251:4576/", "us-east-2");
        AmazonSQS amazonSQSClient = AmazonSQSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withEndpointConfiguration(endpoint).build();
        return amazonSQSClient;
    }

    @Bean
    public ProducerTemplate producerTemplate(CamelContext camelContext){
        ProducerTemplate template = camelContext.createProducerTemplate();
        return template;
    }

    @Bean(name="msgGrpIdStrategy")
    public String getMessageGroupIdStrategy(){
        return "useExchangeId";
    }
}
