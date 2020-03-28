# Project Title
LocalStack provides an easy-to-use test/mocking AWS provided Services.<br/>
You can configure/manage the same way we do with AWS. We can either use AWS CLI (or) SDK to communicate from creating the REST API to deploying it to the stage.

## Getting Started
Spin up the AWS Service with the below command from the location where docker-compose located.<br/>
Option 1: Start all services at once.
```
DEBUG=1  docker-compose up
```
Note: After running above command, check if required service has been mocked/started or not, if not, then please go ahead with Option 2.

Option 2: Start Specific Service.
Example: APIGateway
Spin up the APIGateway with the below command from the location where docker-compose located.<br/>
```
DEBUG=1 SERVICES=apigateway docker-compose up
```



## Prerequisites
Download docker-compose.yml file from https://bitbucket.vip.marketaxess.com/projects/EMS/repos/ems-poc/browse/ems-scripts/docker-compose.yml and run it.<br/>
JDK1.8: For Lambda function as of now AWS supports JDK1.8

## Installing
### APIGateway
Spin up the APIGateway with the below command from the location where docker-compose located.<br/>
```
DEBUG=1 SERVICES=apigateway docker-compose up
```
```
com.mktx.sdk.apigtw.APIGatewayConfigClient
```

Run this client to setup the REST API in localstack APIGateway. And get the RESTAPIId from the result.<br/>
Form the URL in the below format.
```
http://10.8.20.84:4567/restapis/4842669065/test/_user_request_/instruments
```
**4842669065**: Rest API ID which we get as a result of CreateRestApi.<br/>
**test**: Stage name which we give as a input of CreateDeployment process.<br/>
**instruments**: Path part which we give as a input of CreateResource process.

### ElasticSearch
Spin up the ElasticSearch with the below command from the location where docker-compose located.<br/>
```
DEBUG=1 SERVICES=es docker-compose up
```

Note: Once elasticsearch service is up, run "ems-elasticsearch-poc" : 
```
https://bitbucket.vip.marketaxess.com/projects/EMS/repos/ems-poc/browse/ems-elasticsearch-poc
```

### Kinesis
Spin up the Kinesis with the below command from the location where docker-compose located.<br/>
```
DEBUG=1 SERVICES=kinesis  docker-compose up
```

Note: Once Kinesis service is up, run "ems-kinesis-consumer-poc" : 
```
https://bitbucket.vip.marketaxess.com/projects/EMS/repos/ems-poc/browse/ems-kinesis-consumer-poc
```
or "ems-kinesis-localstack-poc"

```
https://bitbucket.vip.marketaxess.com/projects/EMS/repos/ems-poc/browse/ems-kinesis-localstack-poc
```

##

## Built With
N/A

## Versioning
N/A
