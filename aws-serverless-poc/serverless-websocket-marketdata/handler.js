console.log('Websocket Request Handler is called');
const AWS = require('aws-sdk');
const request = require("request");
const http = require('http');
var restEndPointInitial = 'http://localhost:5002/marketdepth/';
var websocketHost = "http://localhost:3001";
var websocketDisconnectRouteURL='http://localhost:5002/ws/disconnect?ClientID=';
const apigwManagementApi = new AWS.ApiGatewayManagementApi({endpoint: websocketHost});

const success = {
  statusCode: 200,
  body :'Process is completed'
};

const putsuccess = {
  statusCode: 202
};

module.exports.marketdata= async function (event , context) {
	  console.log("Hello from marketdata Route");
	  console.log('JSON:'+JSON.stringify(event));
	  payload=JSON.parse(event.body).payload;
	 // console.log("payload: "+JSON.stringify(payload));
	  instid=JSON.parse(JSON.stringify(payload)).instId;
	   console.log("instid: "+instid);
	  connection_id =event.requestContext.connectionId;
	  console.log('connection_id:'+connection_id); 
	 
	 var URL = restEndPointInitial + `?EMS_INST_ID=${instid}&ClientID=${connection_id}`;
	 console.log("URL:"+URL);
	 
	 request(URL, function (error, response, body) {
	 console.log('error:', error); // Print the error if one occurred
	 console.log('statusCode:', response && response.statusCode); // Print the response status code if a response was received
	 console.log('body:', body); // Print the HTML for the Google homepage.
	 const params = {ConnectionId: connection_id,
                Data: body};
	 apigwManagementApi.postToConnection(params, () => {});
});
	return apigwManagementApi;
};

module.exports.disconnect= async function (event , context) {
		console.log("Hello from disconnect Route");
		console.log("requestContext: "+ event.requestContext);
		connection_id =event.requestContext.connectionId
		console.log('connection_id:'+connection_id);  
		pathofWsUrl=websocketDisconnectRouteURL+`${connection_id}`;
		console.log('endpoint:'+pathofWsUrl);
		const options = {
			url: pathofWsUrl,
			method: 'PUT'
		};
		request(options,  (res)=>  {
			console.log('Function is called under disconnect route.'); 
		}); 
		console.log('Disconnect Route is Completed!!!'); 
		const params = {ConnectionId: connection_id,
                Data: "disconnected socket"};
	 apigwManagementApi.postToConnection(params, () => {});
    return success;
};

module.exports.connect= async event => {
	console.log('ConnId:'+event.requestContext.connectionId);
	console.log("hello:  connect Route");
    return success;
};

module.exports.default= async event =>{
	console.log('ConnId:'+event.requestContext.connectionId);
	console.log("hello:  default Route");
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