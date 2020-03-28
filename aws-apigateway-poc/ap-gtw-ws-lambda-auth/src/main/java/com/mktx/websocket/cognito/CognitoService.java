package com.mktx.websocket.cognito;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CognitoService {

    AWSCognitoIdentityProvider cognitoClient =
            AWSCognitoIdentityProviderClientBuilder.standard().withRegion(Regions.US_EAST_2).build();

    public boolean ifLoggedIn(String accessToken) {
		GetUserRequest request = new GetUserRequest();
		request.withAccessToken(accessToken);
		try {
			GetUserResult result = cognitoClient.getUser(request);
		} catch (Exception e) {
			log.warn("User is not logged in", e);
			return false;
		}
		return true;
    }

}
