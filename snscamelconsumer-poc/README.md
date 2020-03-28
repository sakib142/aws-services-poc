# Project Title

AWS Dynamo Lambda Demo

## Getting Started

Check out the project and follow below steps.

1. Create one lambda function to insert data to dynamo db and subscribe it to SNS.

2. Create SNS topic and subscribe it to one of the SQS queues.

3. Replace queue, sns urls and aws access and secret key with your own in AwsCredentials.properties file.

4. Create a dynamo db table named 'Order'.

4. Do maven clean install

5. Upload the jar and execute the lambda function



### Prerequisites

AWS Account with proper credentials to access sns


 
