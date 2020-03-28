# Project Title

API Gateway Websocket Support through Serverless framework

To run websocket functionality of API Gateway, we are going to use Serverless framework, in which APiGateway, lambda is supported.

Note: This whole framework is developed in Node.js, and hence for our requirement for websocket, we had to write some custom handler for custom route that we needed.


## Getting Started

1. Get the application code from bitbucket in your local.
2. In serverless.yml file, change the routeName that you want under:

```
functions:
  connect:
    handler: handler.connect
    events:
      - websocket:
          route: $connect
      - websocket:
          route: $default

   # manage custom routes     
  orders:
    handler: handler.orders
    events:
      - websocket:
          route: orders
```

For example in above example the custom route is "orders"

3 Open CMD/Terminal at the root location of this project and run below command

```>npm install```
	
4. start serverless components to bring websocket up.

	```
	> serverless offline start | or | sls offline start
	```

Once it starts the server it will bring websocket endpoint and http endpoint up.
make a note of both endpoints:
Defaults:
websocket endpoint[default port: 3001]: wss://localhost:3001
http endpoint[default port: 300]: http://localhost:3000
4. In handle.js file, you need to change endpoint details:
4.1 AWS.ApiGatewayManagementApi({endpoint: "http://localhost:3001"});
==> Above url denotes on which port websocket is listening, we need to mention endpoint with that port to send back the response to client.
4.2 request('http://localhost:5003/order/FIRM2', function (error, response, body)
==> above http endpoint is our target http endpoint that we want to hit to get response.

5. Communicate via websocket

5.1 Use websocket client to connect to websocket:
```
eg: wscat -c wss://localhost:3001 -n 
or 
wscat -c ws://localhost:3001
```
5.2 Once connected then invoke route:
> {"action":"orders"}

You will receive response of http endpoint mentioned in 4.2
~~~~~~~~~


### Prerequisites
~~~~~~~~~
1. Node.js to be install:
~~~
https://nodejs.org/dist/v10.16.0/node-v10.16.0-x64.msi
~~~
2. Install Serverless components:
 follow steps mentioned above: Under Getting started

3. install wscat.
~~~
>npm install --save wscat
~~~
Examples: follow steps above and run this application.

~~~~~~~~~
### Installing
~~~~~~~~~
FOllow above steps along with this application and run the code using wscat.

~~~~~~~~~
## Built With
~~~~~~~~~
This serverless application is built on the top of Node.js 10
 ~~~~~~~~~

