package com.mktx.camel.component.aws.kinesis;
/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License"). You may not use this file except in compliance with the
 * License. A copy of the License is located at
 *
 * http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with
 * the License. A copy of the License is located at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.kinesis.common.ConfigsBuilder;
import software.amazon.kinesis.common.InitialPositionInStreamExtended;
import software.amazon.kinesis.coordinator.Scheduler;
import software.amazon.kinesis.metrics.MetricsConfig;
import software.amazon.kinesis.retrieval.RetrievalConfig;
import software.amazon.kinesis.retrieval.polling.PollingConfig;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class KCL2Consumer {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private Scheduler scheduler;
    private KinesisEndpoint endpoint;
    private Processor processor;

    public KCL2Consumer(KinesisEndpoint endpoint, Processor processor) {
        this.endpoint = endpoint;
        this.processor = processor;
    }

    /**
     * init method.
     */
    public void init() {

        KinesisConfiguration configuration = endpoint.getConfiguration();
        Region region = Region.of(configuration.getRegion());
        String streamName = configuration.getStreamName();
        String applicationName = configuration.getApplicationName() == null ? streamName : configuration.getApplicationName();

        /**
         * Sets up configuration for the KCL, including DynamoDB and CloudWatch dependencies. The final argument, a
         * ShardRecordProcessorFactory, is where the logic for record processing lives.
         */
        DynamoDbAsyncClient dynamoClient =
                DynamoDbAsyncClient.builder().region(region).build();
        CloudWatchAsyncClient cloudWatchClient = CloudWatchAsyncClient.builder().region(region).build();
        ConfigsBuilder configsBuilder = new ConfigsBuilder(streamName,
                applicationName, configuration.getAmazonKinesisClient(), dynamoClient,
                cloudWatchClient, UUID.randomUUID().toString(), new KCL2RecordProcessorFactory(endpoint, processor));

        /**
         * Cloudwatch metrics level configuration, by default NONE
         */
        MetricsConfig metricsConfig = configsBuilder.metricsConfig().metricsLevel(configuration.getMetricsLevel());

        /**
         * Configuring Iteratortype, by default TRIM_HORIZON to receive data from begining
         */
        RetrievalConfig retrievalConfig = configsBuilder.retrievalConfig()
                .initialPositionInStreamExtended(InitialPositionInStreamExtended
                        .newInitialPosition(configuration.getIteratorType()));

        /**
         * In case of Enhanced fan-out disabled configure Polling config so that
         * KCL2 uses GetShardIterator & GetRecords API's to receive data from streams
         */
        boolean isFanoutEnabled = configuration.isFanout();
        if (!isFanoutEnabled) {
            retrievalConfig = retrievalConfig.retrievalSpecificConfig(new PollingConfig(streamName, configuration.getAmazonKinesisClient()));
        }

        scheduler = new Scheduler(configsBuilder.checkpointConfig(), configsBuilder.coordinatorConfig(),
                configsBuilder.leaseManagementConfig(), configsBuilder.lifecycleConfig(),
                metricsConfig, configsBuilder.processorConfig(), retrievalConfig);

        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.setDaemon(true);
        schedulerThread.start();
        log.info("KCL2 Consumer Setup started ::: StreamName:{} ApplicationName:{} Enhanced Fan-Out:{}", streamName, applicationName, isFanoutEnabled);

    }

    /**
     * Do clean up process
     * <p>
     * Checkpoints the last processed record into DynamoDB
     *
     * <p>
     * Note: This method triggered only in case of Graceful Shutdown.
     * </p>
     */
    public void destroy() {
        /**
         * Stops consuming data. Finishes processing the current batch of data already received from Kinesis before
         * shutting down.
         */
        Future<Boolean> gracefulShutdownFuture = scheduler.startGracefulShutdown();
        log.info("Waiting up to 20 seconds for shutdown to complete.");
        try {
            gracefulShutdownFuture.get(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.info("Interrupted while waiting for graceful shutdown. Continuing.");
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("Exception while executing graceful shutdown.", e);
        } catch (TimeoutException e) {
            log.error("Timeout while waiting for shutdown.  Scheduler may not have exited.");
        }
        log.info("Completed, shutting down now.");
    }

}
