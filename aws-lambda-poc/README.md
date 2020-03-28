Project Title
~~~~~~~~~
This project is a proof of concept for AWS Provided Lambda function, whose job is to trigger a code on a particular event.
In this POC, we have functionality of pushing Data to DynamoDB upon invokation of this lambda function, which is subscribed to "DynamoDBEvent".
Follow the step by step instrunctions mentioned in document: https://marketaxess.atlassian.net/wiki/spaces/EMS/pages/38895749/AWS+Lambda+Function

~~~~~~~~~
Getting Started
~~~~~~~~~

1. Get this code in your machine and first thing to do is to get secret/access key from AWS: Goto AWS IAM-->User-->Select User-->Security Credential--> copy  AccessKey and you can generate secret key you will get it in pair
2. Enter both keys into resource/application.properties file.
2. Compile it and generate shaded-jar.
2. Create a Lambda Function which will listen DynamoDBEvent.
3. Upload shaded-jar to S3 or directly to AWS lambda function created.
4. Create a DynamoDB table having same column as Entity: IncomingFixMessage [Make sure to have same table name as mentioned in entity]
5. trigger the lambda function from AWS platform and check record in DynamoDB Table: IncomingFixMessage.

Note: Please follow the instrunctions mentioned in document:  https://marketaxess.atlassian.net/wiki/spaces/EMS/pages/38895749/AWS+Lambda+Function

~~~~~~~~~
Prerequisites
~~~~~~~~~
JDK1.8: For Lambda function as of now AWS supports JDK1.8

~~~~~~~~~
Installing
~~~~~~~~~
1. Get aws-lambda-poc code from ems-poc bitbucket repo
2. Use SourceTree to create clone of the project in your local machine.
3. Compile the code and generated shaded-jar and upload the same to AWS lambda.

~~~~~~~~~
Running the tests
~~~~~~~~~
1. Configure DynamoDB event to Lambda created
2. Click on Test button and check the result in IncomingFixMessage table.
3. This test will simply insert some rows to DynamoDB table.
4. Verify the result and also can check Monitoring section of Lambda to visualize all resources being used along with debug log

~~~~~~~~~
Deployment
~~~~~~~~~
1. Once you have generated jar after bulding the project, you can:
1.1 Upload shaded-jar to AWS S3 in same region where lambda exists
1.1.2 Copy URL for the S3 upload and go to Lambda function
1.1.3 Goto Upload section of Lambda HOme page and select URL from S3 from down
1.1.4 paste S3 URL and save the function by pressing Save Button.
1.2 Upload shaded-jar directly to lambda
1.2.1 select the default option from drop down
1.2.2 Upload shaded-jar to Lambda directly and save the function.

~~~~~~~~~
Testing Against LocalStack
~~~~~~~~~
1. Run the LocalStack and make sure "Mock Lambda Service" running.
2. Through command prompt, navigate to the location where shaded-jar resides.
3. Run the create-function command below.
aws lambda create-function --region us-east-1 --function-name api-test-lambda --runtime java8 --handler com.mktx.lambda.LambdaHandler --memory-size 128 --zip-file fileb://shaded-jar.jar --role arn:aws:iam::123456:role/role-name --endpoint-url=http://10.8.20.84:4574

**handler** : Fully Qualified Name of Handler Class.

4. Invoke it with invoke command below.
aws lambda invoke --function api-test-lambda --payload '{"name":"EMS"}' lambda.out --endpoint-url=http://10.8.20.84:4574

**api-test-lambda** : Function name given in the create-function command.
**payload** : Lambda input in  json format.
**lambda.out** : File name to store the lambda logs including the lambda output. (This file will be created in the current path by default)

~~~~~~~~~
Built With
~~~~~~~~~
Maven - Dependency Management
Project is using maven distribution to build the project also, it has shaded plugin to create shaded jar which is used along with lambda.

~~~~~~~~~
Versioning
~~~~~~~~~
TBD
