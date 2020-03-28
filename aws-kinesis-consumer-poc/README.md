# Kinesis Consumer POC

A consumers using the Amazon Kinesis Data Streams API with the AWS SDK for Java. The Java example code in this section
demonstrates how to perform basic Kinesis Data Streams API operations, and is divided up logically by operation type.
By default, shards in a stream provide 2 MiB/sec of read throughput per shard. This throughput gets shared across all the consumers that are reading from a given shard.
In other words, the default 2 MiB/sec of throughput per shard is fixed, even if there are multiple consumers that are reading from the shard.

## Getting Started
•	After copying project and do local setup. We can execute the application as spring-boot.
•	Once application run successfully, with the POSTMAN we can perform all task.

### Prerequisites
•	We should have an account in AWS console.
•	We should have an Kinesis data stream and provide that in properties file.
•	We should have to provide access_key,secret_key and region in properties file.
•   We need to provide the input shard-ID,sequenceNumber,shardIteratorType as required for search.
•	All maven library should have downloaded properly.
•	If required check proxy connection as well.
•	Application should have spring-boot web component to run locally tomcat.
Note: - some time might face issue related to ssl while executing, Please install ssl certificate "https://kinesis:us-east-2.amazonaws.com"
```
Examples

```

### Execution

Sample Execution screen shot –
1.	GET –

	steps - 
	1. select post method and URL will http://localhost:8080/ [port will be local tomcat server port]
	2. i/p like as -
				{
                	"shardId":"shardId-000000000001",
                	"sequenceNum":"49596813507191112078064637726201122439686465933996458002",
                	"shardIteratorType":"AT_SEQUENCE_NUMBER"
                }
	3. o/p -  Response code 200 with success message "Data fetched from kinesis." In console we can see the data as per
	  search query.

Note :  If no records are returned, that means no data records are currently available from this shard at the sequence
 number referenced by the shard iterator. In this situation, we should wait for an amount of time that's
  appropriate for the data sources for the stream.



	
```
Lambda Sample - 

```



## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

