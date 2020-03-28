# EMS ElasticCache Redis POC

We can write or delete in redis cache with elastic cache redis service provided by AWS through JAVA application.
We will use POSTMAN to perform all task by providing local link. We can also generate shaded-jar and deploy the same code
in AWS console and perform all task through LAMBDA function.

## Getting Started
•	After copying project and do local setup. We can execute the application as spring-boot.

•	We can execute this application with Lambda function to access AWS redis or
    can use Postman to connect with other end points.

Note : - AWS Redis can be access with Lambda.

### Prerequisites
<li>	We should have an account in AWS console.
<li>	We should have an Elastic cache redis and provide the host name (endpoint) in properties file.
<li>	All maven library should have downloaded properly.
<li>	If required check proxy connection as well.
<li>	Application should have spring-boot web component to run locally tomcat.

Example
```
spring.redis.host=<redis-cache-ems>.mi0spi.ng.0001.use2.cache.amazonaws.com
```

### Execution

#### Lambda  -


1. Generate shaded-jar with the help command mvn [clean install -P shaded-jar].

2. Open aws console -> lambda-> create lambda function -> upload jar -> select language as JAVA -> provide handler request.

3. create test event for lambda to test all functions.

###### GET from redis -
Steps :-
1. Create a lambda function through AWS console.
2. upload the respective jar.
3. Provide the role wrt function.
4. Configure input like -
```
    {
      "method": "GET",
      "data": {
        "id": -1,
        "sourceVenue": "TEST",
        "instId": -123
      },
      "connectionID": "56789"
    }
```
5. output --
```
    {
        "connectionIDs": [
            "56789"
        ]
    }
```
###### Delete from redis -
Steps :-
1. Create a lambda function through AWS console.
2. upload the respective jar.
3. Provide the role wrt function.
4. Configure input like -
```
    {
     "method": "DELETE",
     "data": {
       "id": -1,
       "sourceVenue": "TEST",
       "instId": -123
     },
     "connectionID": "56789"
    }
```
5. output -- In console message "DELETED".
	          In Log message - Evicting the socket-connections cache for the InstID.
    

#### Docker Configuration

1. Configure docker compose file for ems-redis image as below -
```
   ems_redis:
       image: redis
       container_name: "ems_cache"
       ports:
         - "6379:6379"
```
2. Run the docker by 
```
docker-compose up
```
3. Change the property file for 
```
    spring.redis.host=10.8.*.*
``` 
 Note:- Please verify docker must run with the ems-redis image before execution with postman

##### Postman Execution –

###### GET

Steps :-
1. select get method and URL will "http://localhost:8080/" [port will be local tomcat server port]
2. input like as -

```
    {
       "method": "GET",
       "data": {
         "id": -1,
         "sourceVenue": "TEST",
         "instId": -123
       },
       "connectionID": "56789"
     }
```
<li> output -
  
 ``` 
    {
        "connectionIDs": [
            "56789"
        ]
    }

```
###### Delete –

Steps :-   
1. select delete method and URL will http://localhost:8080/ [port will be local tomcat server port]
2. input like as -
```
    {
         "method": "DELETE",
         "data": {
           "id": -1,
           "sourceVenue": "TEST",
           "instId": -123
         },
         "connectionID": "56789"
      }
```

<li> output -  Response code 200 with success message - DELETED

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

