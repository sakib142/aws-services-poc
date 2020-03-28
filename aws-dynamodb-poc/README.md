# DynamoDB

This project is a sample code for performing CRUD operations with DynamoDB.

## Getting Started

Part 1. https://marketaxess.atlassian.net/secure/RapidBoard.jspa?rapidView=84&modal=detail&selectedIssue=EMS2-18

```
mvn clean install -P shaded-jar
```

Part 2. https://marketaxess.atlassian.net/browse/EMS2-4 

Need to run this poc against the LocalStack 

### Prerequisites

We need to have docker setup locally and you should be able to run usual docker commands on local system.

```
docker run hello-world
```

### Installing

Part 2 . Running against LocalStack
<li> Download docker compose file from Bitbucket 

[ems-scripts](https://bitbucket.vip.marketaxess.com/projects/EMS/repos/ems-poc/browse/ems-scripts) and place it under some directory and go to that directory  
<li> Run <code> docker-compose up </code> in order to run LocalStack service locally.
<li> Change application properties file in order to point to LocalStack's DynamoDB service as below

```
amazon.dynamodb.endpoint=http://<IP_ADDRESS_VM>:4569
#IP is address where LS service is running
``` 
<li> Run MarketDepthApplication as normal spring boot application
<li> In MarketDepthApplication file <code>marketDepthService.createTable("MarketDepthTb");</code> would be the one which creates table in LocalStack
<li> After first execution 

1. Either you can comment the above line as that would have created the table already 
2. Or you can delete the table with AWS CLI commands
3. Or you can re run LS docker compose command to freshly start off the service 
<li> You should be able to see the console output of already written service (of Part1)

## Running the tests

Run MarketDepthApplication as normal spring boot application and console output will display at the end

```
2019-07-01 11:51:58.834  INFO 4504 --- [           main] c.t.e.m.lambdahandler.MarketDepthlambda  : MarketDepthlambda.apply()-
Finished Localstack operations.
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [LocalStack](https://github.com/localstack/localstack) - A fully functional local AWS cloud stack. Develop and test your cloud & Serverless apps offline!


## Versioning

We use bitbucket for versioning. 





 
