package com.mktx.camel.component.aws.kinesis;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.util.CastUtils;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import software.amazon.kinesis.exceptions.InvalidStateException;
import software.amazon.kinesis.exceptions.ShutdownException;
import software.amazon.kinesis.lifecycle.events.*;
import software.amazon.kinesis.processor.ShardRecordProcessor;
import software.amazon.kinesis.retrieval.KinesisClientRecord;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class KCL2RecordProcessor implements ShardRecordProcessor {

    private static final String SHARD_ID_MDC_KEY = "ShardId";
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private String shardId;

    private KinesisEndpoint endpoint;

    private Processor processor;

    public KCL2RecordProcessor(KinesisEndpoint endpoint, Processor processor) {
        this.endpoint = endpoint;
        this.processor = processor;
    }

    /**
     * @param initializationInput initializationInput
     */
    public void initialize(InitializationInput initializationInput) {
        shardId = initializationInput.shardId();
        MDC.put(SHARD_ID_MDC_KEY, shardId);
        try {
            log.info("Initializing @ Sequence: {}", initializationInput.extendedSequenceNumber());
        } finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

    /**
     * @param processRecordsInput processRecordsInput
     */
    public void processRecords(ProcessRecordsInput processRecordsInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);
        try {
            log.info("Processing {} record(s)", processRecordsInput.records().size());
            Queue<Exchange> exchanges = createExchanges(processRecordsInput.records());
            processBatch(CastUtils.cast(exchanges));
            processRecordsInput.checkpointer().checkpoint();
        } catch (Exception e) {
            log.error("Caught throwable while processing records. Aborting.", e);
            return;
        } finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

    public void processBatch(Queue<Object> exchanges) throws Exception {
        while (!exchanges.isEmpty()) {
            final Exchange exchange = ObjectHelper.cast(Exchange.class, exchanges.poll());

            log.info("Processing exchange [{}] started.", exchange);
            processor.process(exchange);
        }
    }

    private Queue<Exchange> createExchanges(List<KinesisClientRecord> records) {
        Queue<Exchange> exchanges = new ArrayDeque<>();
        for (KinesisClientRecord record : records) {
            exchanges.add(endpoint.createExchange(record));
        }
        return exchanges;
    }


    /**
     * @param leaseLostInput leaseLostInput
     */
    public void leaseLost(LeaseLostInput leaseLostInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);
        try {
            log.info("Lost lease, so terminating.");
        } finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

    /**
     * @param shardEndedInput shardEndedInput
     */
    public void shardEnded(ShardEndedInput shardEndedInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);
        try {
            log.info("Reached shard end checkpointing.");
            shardEndedInput.checkpointer().checkpoint();
        } catch (ShutdownException | InvalidStateException e) {
            log.error("Exception while checkpointing at shard end. Giving up.", e);
        } finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

    /**
     * @param shutdownRequestedInput shutdownRequestedInput
     */
    public void shutdownRequested(ShutdownRequestedInput shutdownRequestedInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);
        try {
            log.info("Scheduler is shutting down, checkpointing.");
            shutdownRequestedInput.checkpointer().checkpoint();
        } catch (ShutdownException | InvalidStateException e) {
            log.error("Exception while checkpointing at requested shutdown. Giving up.", e);
        } finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }
}
