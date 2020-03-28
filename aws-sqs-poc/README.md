# Project Title

ems-sqs-poc

## Getting Started

Check out the project and follow below steps.

To access Aws Sqs :

1. Replace queue urls and aws access and secret key with your own in application.properties file.
2. Do maven clean install
3. Give input to SourceQue
4. Run the main class SqsCamelDemoApplication
5. Observe the output in TargetQueue

To access Localstack Sqs :

1. If you are working on VM, create source and target queues using aws CLI before you run this application
2. Do maven clean install
3. Observe the input is given to SourceQueue in main class
3. Run the main class SqsCamelDemoApplication 
4. Observe the output in TargetQueue




### Prerequisites

AWS Account with proper credentials to access sqs.

Localstack should be up and running in your machine.
Spinup the localstack sqs service using docker compose file. 



 
