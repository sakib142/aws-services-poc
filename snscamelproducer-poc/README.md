# Project Title

AWS SNS Camel Demo

## Getting Started

Check out the project and follow below steps.

1. Create couple of queues in sqs using aws console.
2. Create SNS topic and subscribe it to one of the queues.
3. Create one lambda function to insert data to dynamo db and subscribe it to SNS
4. Replace queue urls and aws access and secret key with your own in application.properties file.
5. Do maven clean install
6. Run the main class SnsCamelDemoApplication 


### Prerequisites

AWS Account with proper credentials to access sns


 
