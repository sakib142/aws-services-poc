package com.mktx.camel.component.aws.kinesis;

import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.kinesis.processor.ShardRecordProcessor;
import software.amazon.kinesis.processor.ShardRecordProcessorFactory;

public class KCL2RecordProcessorFactory implements ShardRecordProcessorFactory {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private KCL2RecordProcessor sampleRecordProcessor;
    private KinesisEndpoint endpoint;
    private Processor processor;

    public KCL2RecordProcessorFactory(KinesisEndpoint endpoint, Processor processor) {
        this.processor = processor;
        sampleRecordProcessor = new KCL2RecordProcessor(endpoint, processor);
    }

    /**
     * @return ShardRecordProcessor
     */
    public ShardRecordProcessor shardRecordProcessor() {
        return sampleRecordProcessor;
    }
}
