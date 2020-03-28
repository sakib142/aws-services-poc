console.log('Handler is called');
const AWS = require('aws-sdk');
const request = require("request");
const success = {
  statusCode: 200
};

module.exports.orders= async function (event , context) {
	  console.log("Hello from orders Route");
	  connection_id =event.requestContext.connectionId
	  console.log('connection_id:'+connection_id)  
	  var request = require('request');
	  const apigwManagementApi = new AWS.ApiGatewayManagementApi({endpoint: "http://localhost:3001"});

	request('http://localhost:5003/order/FIRM2', function (error, response, body) {
	console.log('error:', error); // Print the error if one occurred
	console.log('statusCode:', response && response.statusCode); // Print the response status code if a response was received
	console.log('body:', body); // Print the HTML for the Google homepage.
	const params = {ConnectionId: connection_id,
                Data: body};
	apigwManagementApi.postToConnection(params, () => {});
});
	return apigwManagementApi;
}

module.exports.disconnect= async event =>{
	  console.log('ConnId:'+event.requestContext.connectionId);
	console.log("hello:  disconnect Route");
	console.log("message pr:"+ event);
    return success;
};

module.exports.connect= async event =>{
	console.log('ConnId:'+event.requestContext.connectionId);
	console.log("hello:  connect Route");
    return success;
};

module.exports.default= async event =>{
	console.log('ConnId:'+event.requestContext.connectionId);
	console.log("hello:  connect Route");
    callback(null);
};

module.exports.redirect = function (event, context, callback) {
	console.log('ConnId:'+event.requestContext.connectionId);
    callback(null, {
        statusCode: '200',
        body: JSON.stringify({key: 'hello world'}),
        headers: {
            'Content-Type': 'application/json',
        },
    });
};