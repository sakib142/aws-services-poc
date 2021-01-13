# Project Title

This project is a proof of concept for AWS Cognito Data Sync across region, as part of this POC< we will create a user [Signup]
by decrypting the data from dynamoDB table.

## Getting Started

1. Get this code in your machine.
2. Compile it and generate shaded-jar.
3. Upload shaded-jar to S3 or directly to AWS lambda function created.
4. trigger the lambda function from AWS platform and check the output upon execution.

## Prerequisites
JDK1.8: For Lambda function as of now AWS supports JDK1.8

## Installing
1. Use SourceTree/cmd to clone ems-poc in your local machine.
2. Navigate to ems-poc\ems-cognito-createuser-poc directory.
3. Please mention properties in ``application.properties`` file:
amazon.dynamodb.endpoint=<AWS_DYNAMODB_ENDPOINT>
amazon.aws.accesskey=<AWS_ACCESS_KEY>
amazon.aws.secretkey=<AWS_SECRET_KEY>
amazon.aws.region=<AWS_REGION>
mktx.userpool=<AWS_COGNITO_POOL_ID>
mktx.encrypt.key=<USER_DEFINED_DYNAMODB_USER_COGNITO_ENCRYPTION_KEY>
4. Please mentioned properties in ``AwsCredentials.properties`` file:
accessKey=<AWS_ACCESS_KEY>
secretKey=<AWS_SECRET_KEY>
5. Do maven build for shaded jar(mvn clean install -P shaded-jar) and upload the same to AWS lambda.

## Running the tests
### User Signup
#### Option 1: Configure it as trigger to GL table: CognitoUser
1. We have configured this lambda application as trigger to GL table: CognitoUser.
2. Configure the Handler as **com.trax.ems.cognito.lambdahandler.CognitoCreateUserLambdaHandler**
3. As soon as user is created via Lambda [POC:ems-cognito-replicateuser-poc], data will be replicated and this lambda will be triggered automatically passing the partition keys as input which this lambda will use and read data out of GL table and create user in deployed region.

#### Option 2: Schedule this lamda to execute on periodic basis
1. for that we need to change logic rather than reading from stream, we will be querying out of dynamoDB.



## Deployment
1. Once you have generated jar after bulding the project, you can:
1.1 Upload shaded-jar to AWS S3 in same region where lambda exists
1.1.2 Copy URL for the S3 upload and go to Lambda function
1.1.3 Goto Upload section of Lambda Home page and select URL from S3 from down
1.1.4 paste S3 URL and save the function by pressing Save Button.
1.2 Upload shaded-jar directly to lambda
1.2.1 select the default option from drop down
1.2.2 Upload shaded-jar to Lambda directly and save the function.

## Built With
Maven - Dependency Management
Project is using maven distribution to build the project also, it has shaded plugin to create shaded jar which is used along with lambda.

## Versioning
TBD
