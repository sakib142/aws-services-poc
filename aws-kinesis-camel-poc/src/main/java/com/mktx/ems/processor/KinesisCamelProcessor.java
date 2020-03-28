package com.mktx.ems.processor;

import com.mktx.ems.model.Order;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import software.amazon.kinesis.retrieval.KinesisClientRecord;

import java.nio.charset.StandardCharsets;

@Component
public class KinesisCamelProcessor {

    public String process(Exchange exchange) throws InterruptedException {
        KinesisClientRecord record = exchange.getIn().getBody(KinesisClientRecord.class);
        String data = StandardCharsets.UTF_8.decode(record.data()).toString();
        /*for(int i=0; i<100; i++) {
            System.out.println(i);
            Thread.sleep(1000);
        }*/
        return data;
    }

}
