package com.mktx.websocket.handler;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.mktx.websocket.cognito.CognitoService;

import com.mktx.websocket.model.AuthorizationEffect;
import com.mktx.websocket.model.AuthorizationPolicy;
import com.mktx.websocket.model.AuthorizationStatement;
import com.mktx.websocket.model.AuthorizerResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebsocketAuthHandler implements RequestHandler<APIGatewayProxyRequestEvent, AuthorizerResponse> {

    CognitoService cognitoService = new CognitoService();

    public AuthorizerResponse handleRequest(APIGatewayProxyRequestEvent request, Context context) {

        log.info("Request: {} and Context: {}", request, context);

        Map<String, String> headers = request.getHeaders();

        String accessToken = headers.get("Authorization");
        log.info("Authorization token received:{} ", accessToken);

        APIGatewayProxyRequestEvent.ProxyRequestContext proxyContext = request.getRequestContext();
        APIGatewayProxyRequestEvent.RequestIdentity identity = proxyContext.getIdentity();

        AuthorizerResponse response = new AuthorizerResponse();
        AuthorizationPolicy policy = new AuthorizationPolicy();

        List<AuthorizationStatement> Statement = new LinkedList<>();
        AuthorizationStatement stmt = new AuthorizationStatement();
        stmt.Action = "*";
        stmt.Resource = "*";
        Statement.add(stmt);

        policy.Version = "2012-10-17";
        policy.addStatement(stmt);
        response.setPrincipalId("1234");
        response.setPolicyDocument(policy);

        if(cognitoService.ifLoggedIn(accessToken)){
            stmt.Effect = AuthorizationEffect.Allow;
        } else {
            stmt.Effect = AuthorizationEffect.Deny;
        }
        log.info("Authorization process completed");
        return response;
    }

}
