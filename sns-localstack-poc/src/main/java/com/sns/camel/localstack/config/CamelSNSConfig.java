package com.sns.camel.localstack.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CamelSNSConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${aws.sns.accessKey}")
    private String accessKey;

    @Value("${aws.sns.secretKey}")
    private String secretKey;

    @Value("${aws.sns.endpoint}")
    private String snsEndpoint;

    @Value("${aws.sqs.endpoint}")
    private String sqsEndpoint;

    @Value("${aws.sns.region}")
    private String region;


    @Bean(name = "snsClient")
    public AmazonSNS getSNSClient(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(snsEndpoint, region);
        AmazonSNS amazonSNSClient = AmazonSNSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withEndpointConfiguration(endpoint).build();
        return amazonSNSClient;
    }

    @Bean(name = "sqsClient")
    public AmazonSQS getSQSClient(){
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(sqsEndpoint, region);
        AmazonSQS amazonSQSClient = AmazonSQSClientBuilder.standard().withEndpointConfiguration(endpoint).build();
        return amazonSQSClient;
    }

    @Bean
    public ProducerTemplate getProducerTemplate(CamelContext camelContext){
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        return producerTemplate;
    }
}
