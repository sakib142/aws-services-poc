package com.mktx.ems.lambdaauth.cognito;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.mktx.ems.lambdaauth.exception.CustomException;
import com.mktx.ems.lambdaauth.util.AwsCognitoJwtValidatorUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

import java.net.MalformedURLException;
import java.text.ParseException;

@Slf4j
public class CognitoService {

    public AWSCognitoIdentityProvider getCognitoClient(){
		AWSCognitoIdentityProvider cognitoClient =
				AWSCognitoIdentityProviderClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
		return cognitoClient;
	}

    public boolean isLoggedIn(String accessToken) {
		try {
			getUserResult(accessToken);
		} catch (Exception e) {
			log.warn("User is not logged in", e);
			return false;
		}
		return true;
    }

    public GetUserResult getUserResult(String accessToken) {
		GetUserRequest request = new GetUserRequest();
		request.withAccessToken(accessToken);
		GetUserResult result = getCognitoClient().getUser(request);
		return result;
	}

	public JSONObject verifyTheToken(String IdToken)
			throws MalformedURLException, BadJOSEException, CustomException, ParseException, JOSEException {

		JWTClaimsSet jwtClaimsSet = AwsCognitoJwtValidatorUtil.validateAWSJwtToken(IdToken);
		return jwtClaimsSet.toJSONObject();

	}

	public String getUserRole(String IdToken) {
		String role = null;
		try {
			JSONObject jsonObject = verifyTheToken(IdToken);
			log.info("Decoded JWT : {}",jsonObject);
			role = jsonObject.get("custom:role").toString();
			log.info("role : {}",role);
		}catch (Exception e){
			log.info("Exception caught while verifying the Id token {}", e);
		}

		return role;
	}

}
