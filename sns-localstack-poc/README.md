# Project Title

AWS SNS Camel Localstack POC

## Getting Started

Check out the project and follow below steps.

1. Create a queue in LocalStack sqs as in note section.

2. Create SNS topic or application will automatically create and subscribe it to one of the queues.

3. Replace virtual machine IP with respect to system IP where sqs and sns docker service is running in application.properties file.

4. Do maven clean install.

5. Run the main class SnsCamelDemoApplication 

Note: 

<li> For localstack we must create a queue and a topic before running the application.

```
aws sns create-topic --name UniqueTopic --endpoint-url http://10.8.x.x:4575/
aws sqs create-queue --queue-name UniqueSqs --endpoint-url http://10.8.x.x:4576/

```
<li> For the first time to subscribe the sns to sqs, we need to uncomment the line in propeties file, once it subscribe 
we don't need to change.

```
   # At first time Subscribe SNS to SQS, So that we can receive the message in SQS 
   #aws.sns.url=aws-sns://orderTopic?amazonSNSClient=#snsClient&amazonSQSClient=#sqsClient&subscribeSNStoSQS=true&region=us-east-1&queueUrl=http://10.8.20.122:4576/queue/OrderQueue
```
    


### Prerequisites

<li> Localstack should be up and running in your machine.
<li> Spinup the localstack sqs service using docker compose file.
<li> Must have a sqs queue and a sns topic.
<li> If docker running in VM then create a sqs queue and a sns topic, before running the application. 


 
