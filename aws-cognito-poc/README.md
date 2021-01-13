# Project Title

This project is a proof of concept for AWS Cognito related Lambda function, whose job is to get all the users in a user pool.

## Getting Started

1. Get this code in your machine.
2. Compile it and generate shaded-jar.
3. Upload shaded-jar to S3 or directly to AWS lambda function created.
4. trigger the lambda function from AWS platform and check the output upon execution.

## Prerequisites
JDK1.8: For Lambda function as of now AWS supports JDK1.8

## Installing
1. Use SourceTree/cmd to clone ems-poc in your local machine.
2. Navigate to ems-poc\ems-cognito-poc directory.
3. Do maven build for shaded jar(mvn clean install -P shaded-jar) and upload the same to AWS lambda.

## Running the tests
### User Login History
1. Configure test event for Lambda created as below.<br/>
{
  "username": "ems-dev",
  "userPoolId": "us-east-2_4ESDou1E3"
}
2. Configure the Handler as **com.example.cognito.lambda.UserLoginHistoryHandler::handleRequest**
3. Click on Test button and check the result.
4. This test will display the provided user's login history.

### Force LOGOFF User
1. onfigure test event for Lambda created as below.<br/>
{
  "username": "ems-dev",
  "userPoolId": "us-east-2_4ESDou1E3"
}
2. Configure the Handler as **com.example.cognito.lambda.AdminForceLogOffHandler::handleRequest**
3. Click on Test button and check the result.
4. This test will do global SignOut of the username provided.

### List All Users
1. Configure test event for Lambda created as below.<br/>
**"us-east-2_4ESDou1E3"**
2. Configure the Handler as **com.example.cognito.lambda.UtilityLambda::handleRequest**
3. Click on Test button and check the result.
4. This test will display the user information present in the user pool.
5. Verify the result and also can check Monitoring section of Lambda to visualize all resources being used along with debug log


### Search all user with respect to Firm

Sample Execution –
1.	User creation  –

	steps - 
	1. In Lambda function assign handler CognitoHandler
	2. i/p like as -
				{
	  "username": "ems-test5",
	  "email": "abxxxx@gmail.com",
	  "tempPassword": "qR9Po%NS",
	  "password": "Wxyz_1234",
	  "name": "EMSUSER",
	  "firmName": "firm2",
	  "lastname": "EMS",
	  "phoneNumber": "+9196786456"
	}
	3. o/p -  User Created

 Note :  we can do multiple time to create multiple user.
 
 
2.	Search User with respect to firm  –

	1. In Lambda function assign handler SearchByFirmCognito
	2. i/p like as -
				{
	  "poolId": "us-east-2_4ESDxxxxx",
	  "firmName": "firm1" [provide the firm id for which we want search]
	}
	3. o/p -  List of user will come as json -
			[
	  {
	    "username": "ems-test6",
	    "attributes": [
	      {
	        "name": "sub",
	        "value": "6c0f197d-1e0f-4506-8e82-11e81b16c704"
	      },
	      {
	        "name": "email_verified",
	        "value": "true"
	      },
	      {
	        "name": "name",
	        "value": "EMSUSER"
	      },
	      {
	        "name": "phone_number_verified",
	        "value": "true"
	      },
	      {
	        "name": "phone_number",
	        "value": "+91xxxx330"
	      },
	      {
	        "name": "family_name",
	        "value": "firm1"
	      },
	      {
	        "name": "email",
	        "value": "abankxxxx7@gmail.com"
	      }
	    ],
	    "userCreateDate": 1561980643925,
	    "userLastModifiedDate": 1561980643925,
	    "enabled": true,
	    "userStatus": "FORCE_CHANGE_PASSWORD"
	  },
	  {
	    "username": "ems-test4",
	    "attributes": [
	      {
	        "name": "sub",
	        "value": "42576c02-91a6-455e-9953-b6f0f7b7c5ff"
	      },
	      {
	        "name": "email_verified",
	        "value": "true"
	      },
	      {
	        "name": "name",
	        "value": "EMSUSER"
	      },
	      {
	        "name": "phone_number_verified",
	        "value": "true"
	      },
	      {
	        "name": "phone_number",
	        "value": "+91xxxxxx30"
	      },
	      {
	        "name": "family_name",
	        "value": "firm1"
	      },
	      {
	        "name": "email",
	        "value": "abaxxxxx7@gmail.com"
	      }
	    ],
	    "userCreateDate": 1561980594534,
	    "userLastModifiedDate": 1561980594534,
	    "enabled": true,
	    "userStatus": "FORCE_CHANGE_PASSWORD"
	  }
	]

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
