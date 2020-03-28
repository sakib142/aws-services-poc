package com.mktx.kinesis.emskinesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmsKinesisConsumerApp {
    public static void main(String[] args){
        SpringApplication.run(EmsKinesisConsumerApp.class,args);
    }
}
