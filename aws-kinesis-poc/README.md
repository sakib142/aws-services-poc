# Kinesis Producer POC

A producer puts data records into Amazon Kinesis data streams with java . For example, a web server sending log data to a Kinesis data stream is a producer.
With the Kinesis Producer library we can insert data in to stream. Stream hold the data for minimum 24 hour to 7 days (Cost effective).

## Getting Started
•	After copying project and do local setup. We can execute the application as spring-boot.
•	Once application run successfully, with the POSTMAN we can perform all task.

### Prerequisites
•	We should have an account in AWS console.
•	We should have an Kinesis data stream and provide that in properties file.
•	We should have to provide access_key,secret_key and region in properties file.
•	All maven library should have downloaded properly.
•	If required check proxy connection as well.
•	Application should have spring-boot web component to run locally tomcat.
Note: - some time might face issue related to ssl while executing, Please install ssl certificate "https://kinesis:us-east-2.amazonaws.com"
```
Examples

```

### Execution

Sample Execution screen shot –
1.	POST –

	steps - 
	1. select post method and URL will http://localhost:8080/ [port will be local tomcat server port]
	2. i/p like as -
				{
                	"data":"check  1589"
                }
	3. o/p -  Response code 200 with success message "Data inserted into kinesis successfully."

Note :  In console we can see the shardId and sequence number of inserted data.



	
```
Lambda Sample - 

```



## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

