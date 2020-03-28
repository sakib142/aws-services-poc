# Project Title

api-gtw-http-lambda-auth

## Getting Started

Authorization using http lambda authorizer has been implemented as a part of this poc. 
We will use this authorizer in Apigateway to enhance the request from client and pass it to the underlying services. 

### Prerequisites

Create any service which is going to be used by the client and expose the endpoint using apigateway.
Use this project as a lambda function which authorizes and gets the user name of the client using access token.


### Installing

1. Checkout the project
2. Do maven clean install
3. Deploy the jar for lambda function
4. Pass the access token in the header with the name "Authorization"
5. Observe the authorization for your service. 
