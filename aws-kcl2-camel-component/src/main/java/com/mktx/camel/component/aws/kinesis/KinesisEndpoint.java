/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mktx.camel.component.aws.kinesis;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.support.ScheduledPollEndpoint;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.retrieval.KinesisClientRecord;

/**
 * The aws-kcl2 component is for consuming and producing records from Amazon
 * Kinesis Streams through KCL2 library.
 *
 * @author Raju Guduri
 */
@UriEndpoint(firstVersion = "2.17.0", scheme = "aws-kcl2", title = "AWS Kinesis", syntax = "aws-kcl2:streamName", label = "cloud,messaging")
public class KinesisEndpoint extends ScheduledPollEndpoint {

    @UriParam
    private KinesisConfiguration configuration;

    private KinesisAsyncClient kinesisClient;

    private KCL2Consumer consumer;

    public KinesisEndpoint(String uri, KinesisConfiguration configuration, KinesisComponent component) {
        super(uri, component);
        this.configuration = configuration;
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        kinesisClient = configuration.getAmazonKinesisClient() != null ? configuration.getAmazonKinesisClient()
                : createKinesisClient();

        configuration.setAmazonKinesisClient(kinesisClient);
    }

    @Override
    public void doStop() throws Exception {
        if (consumer != null) {
            consumer.destroy();
        }
        super.doStop();
    }

    @Override
    public Producer createProducer() throws Exception {
        return new KinesisProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        consumer = new KCL2Consumer(this, processor);
        consumer.init();
        return null;
    }

    public Exchange createExchange(KinesisClientRecord record) {
        Exchange exchange = super.createExchange();
        exchange.getIn().setBody(record);
        exchange.getIn().setHeader(KinesisConstants.APPROX_ARRIVAL_TIME, record.approximateArrivalTimestamp());
        exchange.getIn().setHeader(KinesisConstants.PARTITION_KEY, record.partitionKey());
        exchange.getIn().setHeader(KinesisConstants.SEQUENCE_NUMBER, record.sequenceNumber());
        return exchange;
    }

    public KinesisAsyncClient getClient() {
        return kinesisClient;
    }

    public KinesisConfiguration getConfiguration() {
        return configuration;
    }

    KinesisAsyncClient createKinesisClient() {
        return KinesisAsyncClient
                .builder().httpClient(NettyNioAsyncHttpClient.builder()
                        .maxConcurrency(Integer.MAX_VALUE)
                        .build())
                .region(Region.of(getConfiguration().getRegion())).build();

    }
}
