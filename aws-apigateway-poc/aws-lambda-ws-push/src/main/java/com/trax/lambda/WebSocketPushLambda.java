package com.trax.lambda;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApi;
import com.amazonaws.services.apigatewaymanagementapi.AmazonApiGatewayManagementApiClient;
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionRequest;
import com.amazonaws.services.apigatewaymanagementapi.model.PostToConnectionResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class WebSocketPushLambda implements RequestHandler<RequestBO, String> {

    @Override
    public String handleRequest(RequestBO input, Context context) {
        pushUpdate(input.getUrl(), input.getConnectionID());
        return "DONE!";
    }

    private void pushUpdate(String wsCallbackURL, String connectionId) {
        AwsClientBuilder.EndpointConfiguration config =
                new AwsClientBuilder.EndpointConfiguration(wsCallbackURL, Region.getRegion(Regions.US_EAST_2).getName());
        AmazonApiGatewayManagementApi client = AmazonApiGatewayManagementApiClient.builder()
                .withEndpointConfiguration(config).build();

        PostToConnectionRequest request = new PostToConnectionRequest();
        request.setConnectionId(connectionId);
        Charset charset = Charset.forName("UTF-8");
        CharsetEncoder encoder = charset.newEncoder();
        ByteBuffer buff = null;
        try {
            buff = encoder.encode(CharBuffer.wrap("Hello From Lambda"));

        } catch (CharacterCodingException e) {
            System.out.println("Error while encoding ");
            e.printStackTrace();
        }
        request.setData(buff);
        PostToConnectionResult result = client.postToConnection(request);
    }

    public static void main(String[] args) {
        WebSocketPushLambda test = new WebSocketPushLambda();
        test.pushUpdate("https://9itz3wof64.execute-api.us-east-2.amazonaws.com/test", "b4mZ4flXiYcCGEA=");
    }


}
