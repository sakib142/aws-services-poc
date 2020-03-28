# elasticSearchTest
We can do all CRUD operation with elasticSearch service provided by AWS through locally as JAVA application. We will use POSTMAN to perform all task by providing local link. We can also generate shaded-jar and deploy the same code in AWS console and perform all task through LAMBDA function.

## Getting Started
•	After copying project and do local setup. We can execute the application as spring-boot.
•	Once application run successfully, with the POSTMAN we can perform all task.

### Prerequisites
•	We should have an account in AWS console.
•	We should have an Elastic Search domain and provide the host name (endpoint) in properties file.
•	All maven library should have downloaded properly.
•	If required check proxy connection as well.
•	Application should have spring-boot web component to run locally tomcat.
```
Examples
elasticsearch.host=search-<name>-5xhieornnbphuzarzgj7v34dva.us-east-2.es.amazonaws.com
```

NOTE: If you want to run this application against LocalStack, then use instruction mentioned in File: ElasticsearchConfig.java before the method and also in properties file.

Also Please make the following change in Config file:ElasticsearchConfig.java

```
RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostName,port,"http")));
```

```
port=4571[default for local elasticsearch]
hostName=ip address/hostname where service is running
```


### Execution

Sample Execution screen shot –
1.	POST –

	steps - 
	1. select post method and URL will http://localhost:8080/ [port will be local tomcat server port]
	2. i/p like as -
				{
			
			"firstName": "elasticUpdateTest",
			"lastName": "checktest1",
			"emails": [
				"elasticUpdateTest@asd.com"
			]
		}
	3. o/p -  Response code 200 with success message 


2.	Get by id –

	steps - 
	1. select get method and URL will http://localhost:8080/ <id> [port will be local tomcat server port]
	
	2. o/p -  Response code 200 with success message and
	
				{
			"id": "232dcaeb-6309-4ad3-9ec7-33119b29269a",
			"firstName": "FirstName",
			"lastName": "LastName",
			"emails": [
				"elasticsearchTest@email.com"
				]
		}

 

3.	Update -
	
	steps - 
	1. select PUT method and URL will http://localhost:8080/ [port will be local tomcat server port]
	2. i/p like as -
					{
			"id": "232dcaeb-6309-4ad3-9ec7-33119b29269a",
			"firstName": "FirstName",
			"lastName": "LastName",
			"emails": [
				"elasticsearchTest@email.com"
			]
		}
	3. o/p -  Response code 200 with success message - UPDATED

	

 

4.	Delete by id – 

	steps - 
	1. select DELETE method and URL will http://localhost:8080/<id> [port will be local tomcat server port]
	
	2. o/p -  Response code 200 with success message - DELETED


Note: After creation of profile we can see count in AWS console to respective Elastic Search domain and vice-versa.



	
```
Lambda Sample - 
```

1. Create Profile -
	
	Steps :- 
	
	1. Create a lambda function through AWS console.
	2. upload the respective jar.
	3. Provide the role wrt function.
	4. Configure i/p like -
	
				{
		  "action": "post",
		  "profileDocument": {
			"firstName": "testLambda2",
			"lastName": "nathtest",
			"emails": "[testLambda1@asd.com]"
		  }
		}

	5. o/p -- In log message that <201 CREATED>.
	
2. Search Profile by ID -

	Steps :- 
	
	1. Create a lambda function through AWS console.
	2. upload the respective jar.
	3. Provide the role wrt function.
	4. Configure i/p like -
		
			{
	  "action": "getById",
	  "profileDocument": {
		"id": "xxxx"
	  }
	}

	5. o/p -- In log message if Profile document will exist with same id then it will print in log.
 
3. Update document --

	Steps :- 
	
	1. Create a lambda function through AWS console.
	2. upload the respective jar.
	3. Provide the role wrt function.
	4. Configure i/p like -
			{
		  "action": "updateById",
		  "profileDocument": {
			"id": "xxxxx",
			"firstName": "xxxxx",
			"lastName": "xxxxx",
			"emails": "[xxx@asd.com]"
		  }
		}

	5. o/p -- In log message that < UPDATED>.
	
	
4. Delete document --

	Steps :- 
	
	1. Create a lambda function through AWS console.
	2. upload the respective jar.
	3. Provide the role wrt function.
	4. Configure i/p like -
					{
				  {
		  "action": "deleteById",
		  "profileDocument": {
			"id": "c6ea6588-55d5-4fe3-937d-e789843517e6"
		  }
		}

	5. o/p -- In log message that < DELETED>.




## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

