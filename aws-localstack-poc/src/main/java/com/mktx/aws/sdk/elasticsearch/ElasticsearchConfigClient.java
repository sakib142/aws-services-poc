package com.mktx.aws.sdk.elasticsearch;

import java.util.concurrent.TimeUnit;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.elasticsearch.AWSElasticsearch;
import com.amazonaws.services.elasticsearch.AWSElasticsearchClientBuilder;
import com.amazonaws.services.elasticsearch.model.CreateElasticsearchDomainRequest;
import com.amazonaws.services.elasticsearch.model.CreateElasticsearchDomainResult;
import com.amazonaws.services.elasticsearch.model.DeleteElasticsearchDomainRequest;
import com.amazonaws.services.elasticsearch.model.DeleteElasticsearchDomainResult;
import com.amazonaws.services.elasticsearch.model.DescribeElasticsearchDomainRequest;
import com.amazonaws.services.elasticsearch.model.DescribeElasticsearchDomainResult;
import com.amazonaws.services.elasticsearch.model.ElasticsearchClusterConfig;
import com.amazonaws.services.elasticsearch.model.ResourceNotFoundException;
import com.amazonaws.services.elasticsearch.model.UpdateElasticsearchDomainConfigRequest;
import com.amazonaws.services.elasticsearch.model.UpdateElasticsearchDomainConfigResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElasticsearchConfigClient {

    public static void main(String[] args) {

        final String domainName = "ems-elasticsearch-poc-domain";

        // Build the client using the default credentials chain.

        AWSElasticsearch client = AWSElasticsearchClientBuilder.standard()
                .withEndpointConfiguration(new EndpointConfiguration("http://10.8.20.96:4578/", "us-east-1"))
                .withCredentials(new DefaultAWSCredentialsProviderChain()).build();

        // Create a new domain, update its configuration, and delete it.
        log.info("Creating Domain...");
        createDomain(client, domainName);

        log.info("Describe domain information...");
        waitForDomainProcessing(client, domainName);

        log.info("Updating Domain...");
        // updateDomain(client, domainName);

        log.info("Deleting Domain...");
        // deleteDomain(client, domainName);



    }

    /**
     * Creates an Amazon Elastic search Service domain with the specified options. Some options require other AWS
     * resources, such as an Amazon Cognito user pool and identity pool, whereas others require just an instance type or
     * instance count.
     *
     * @param client The AWSElasticsearch client to use for the requests to Amazon Elasticsearch Service
     * @param domainName The name of the domain you want to create
     */
    private static void createDomain(final AWSElasticsearch client, final String domainName) {

        // Create the request and set the desired configuration options

        CreateElasticsearchDomainRequest createRequest =
                new CreateElasticsearchDomainRequest().withDomainName(domainName);

        // Make the request.
        log.info("Sending domain creation request..." + createRequest.getDomainName());
        CreateElasticsearchDomainResult createResponse = client.createElasticsearchDomain(createRequest);
        log.info("Domain creation response from Amazon Elasticsearch Service:" + createResponse.toString());
    }

    /**
     * Updates the configuration of an Amazon Elasticsearch Service domain with the specified options. Some options
     * require other AWS resources, such as an Amazon Cognito user pool and identity pool, whereas others require just
     * an instance type or instance count.
     *
     * @param client The AWSElasticsearch client to use for the requests to Amazon Elasticsearch Service
     * @param domainName The name of the domain to update
     */
    private static void updateDomain(final AWSElasticsearch client, final String domainName) {
        try {

            final UpdateElasticsearchDomainConfigRequest updateRequest =
                    new UpdateElasticsearchDomainConfigRequest().withDomainName(domainName)

                            .withElasticsearchClusterConfig(new ElasticsearchClusterConfig().withInstanceCount(3));

            log.info("Sending domain update request...");
            final UpdateElasticsearchDomainConfigResult updateResponse =
                    client.updateElasticsearchDomainConfig(updateRequest);
            log.info("Domain update response from Amazon Elasticsearch Service:");
            log.info(updateResponse.toString());
        } catch (ResourceNotFoundException e) {
            log.info("Domain not found. Please check the domain name.");
        }
    }

    /**
     * Deletes an Amazon Elastic search Service domain. Deleting a domain can take several minutes.
     *
     * @param client The AWSElasticsearch client to use for the requests to Amazon Elasticsearch Service
     * @param domainName The name of the domain that you want to delete
     */
    private static void deleteDomain(final AWSElasticsearch client, final String domainName) {
        try {
            final DeleteElasticsearchDomainRequest deleteRequest =
                    new DeleteElasticsearchDomainRequest().withDomainName(domainName);

            log.info("Sending domain deletion request...");
            final DeleteElasticsearchDomainResult deleteResponse = client.deleteElasticsearchDomain(deleteRequest);
            log.info("Domain deletion response from Amazon Elasticsearch Service:");
            log.info(deleteResponse.toString());
        } catch (ResourceNotFoundException e) {
            log.info("Domain not found. Please check the domain name.");
        }
    }

    /**
     * Waits for the domain to finish processing changes. New domains typically take 10-15 minutes to initialize. Most
     * updates to existing domains take a similar amount of time. This method checks every 15 seconds and finishes only
     * when the domain's processing status changes to false.
     *
     * @param client The AWSElasticsearch client to use for the requests to Amazon Elastic search Service
     * @param domainName The name of the domain that you want to check
     */
    private static void waitForDomainProcessing(final AWSElasticsearch client, final String domainName) {
        // Create a new request to check the domain status.
        final DescribeElasticsearchDomainRequest describeRequest =
                new DescribeElasticsearchDomainRequest().withDomainName(domainName);

        // Check whether the domain is processing, which usually takes 10-15 minutes
        // after creation or a configuration change.
        // This loop checks every 15 seconds.
        DescribeElasticsearchDomainResult describeResponse = client.describeElasticsearchDomain(describeRequest);
        describeResponse.getDomainStatus().getEndpoint();
        while (describeResponse.getDomainStatus().isProcessing()) {
            try {
                log.info("Domain still processing...");
                TimeUnit.SECONDS.sleep(15);
                describeResponse = client.describeElasticsearchDomain(describeRequest);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Once we exit that loop, the domain is available
        log.info("Amazon Elasticsearch Service has finished processing changes for your domain.");
        log.info("Domain description response from Amazon Elasticsearch Service:");
        log.info("EndPoint: " + describeResponse.getDomainStatus().getEndpoint() + "\n " + describeResponse.toString());
    }
}
