package com.trax.ems.elasticSearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
class ElasticsearchConfig {

    @Value("${elasticsearch.host}")
    String hostName;

    @Value("${elasticsearch.port}")
    int port;

    /*
     * For Localstack, please use:
     * "RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostName,port,"
     * http")));" Supply port 4571[default elasticsearch port and IP or hostname.]
     * Above method is for AWS Domain End point which gets created once we create a
     * domain.
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        String scheme = HttpHost.DEFAULT_SCHEME_NAME;
        if (hostName.contains("amazonaws.com")) {
            scheme = "https";
        }
        log.info("MkdServiceBeanConfig.client()-");
        return new RestHighLevelClient(RestClient.builder(new HttpHost(hostName, port, scheme)));
    }
}
