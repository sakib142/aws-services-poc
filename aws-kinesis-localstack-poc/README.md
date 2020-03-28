# Kinesis LocalStack POC

This POC shows how Kinesis can be plugged-in with Local Stack , the functionality is of this POC is in close resemblence with ems-kinsesi-poc, but this POC uses SDKv1 to make Kinesis configurable with localstack 

### Execution
This execution is very similar to that pof ems-kinesis POC. Similar functionality is made configurable with localstack and sdkv1

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

