package com.mktx.ems.lambdaauth.lambdahandler;

import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.mktx.ems.lambdaauth.cognito.CognitoService;
import com.mktx.ems.lambdaauth.model.AuthorizerResponse;
import com.mktx.ems.lambdaauth.model.PolicyDocument;
import com.mktx.ems.lambdaauth.model.Statement;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RoleBasedLambdaAuthorizer implements RequestHandler<APIGatewayProxyRequestEvent, AuthorizerResponse> {

    CognitoService cognitoService = new CognitoService();
    private final String CSM_ROLE = "CSM";

    public AuthorizerResponse handleRequest(APIGatewayProxyRequestEvent request, Context context) {

        Map<String, String> headers = request.getHeaders();
        String accessToken = headers.get("AccessToken");
        String idToken = headers.get("IdToken");
        String firmId = headers.get("FirmId");
        String firmName = headers.get("FirmName");
        log.info("request : {}",request);
        log.info("context : {}",context);
        log.info("Access Token : {}",accessToken);
        log.info("Id Token : {}",idToken);
        log.info("firmId : {}",firmId);
        log.info("firmName : {}",firmName);
        Map<String, String> ctx = new HashMap<String, String>();
        ctx.put("firmId", firmId);
        ctx.put("firmName", firmName);

        APIGatewayProxyRequestEvent.ProxyRequestContext proxyContext = request.getRequestContext();
        APIGatewayProxyRequestEvent.RequestIdentity identity = proxyContext.getIdentity();

        String arn = String.format("arn:aws:execute-api:eu-west-1:%s:%s/%s/%s/%s",
                proxyContext.getAccountId(),
                proxyContext.getApiId(),
                proxyContext.getStage(),
                proxyContext.getHttpMethod(),
                "*");

        System.out.println("arn : "+arn);

        Statement statement = null;


        if(cognitoService.isLoggedIn(accessToken) && cognitoService.getUserRole(idToken).equals(CSM_ROLE)){
            statement = Statement.builder()
                    .effect("Allow")
                    .resource("*")
                    .build();
            GetUserResult result = cognitoService.getUserResult(accessToken);
            System.out.println("User Name in auth Lambda : "+result.getUsername());
            ctx.put("username", result.getUsername());
        }else {
            statement = Statement.builder()
                    .effect("Deny")
                    .resource("*")
                    .build();
        }

        PolicyDocument policyDocument = PolicyDocument.builder()
                .statements(
                        Collections.singletonList(statement)
                ).build();

        return AuthorizerResponse.builder()
                .principalId(identity.getAccountId())
                .policyDocument(policyDocument)
                .context(ctx)
                .build();
    }
}
