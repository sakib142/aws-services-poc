package com.mktx.cognito.lambda;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mktx.cognito.model.CognitoRequest;
import com.mktx.cognito.model.CognitoUser;
import com.mktx.cognito.service.CognitoUserServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("CognitoReplicateUser")
public class CognitoReplicateUserLambda implements Function<CognitoRequest, CognitoUser> {

	@Autowired
	CognitoUserServices cognitoUserService;

	@Override
	public CognitoUser apply(CognitoRequest cognitoRequest) {
		log.info("CognitoHandler.handleRequest()+");
		log.info("Signup Is invoked");
		CognitoUser response = cognitoUserService.signUp(cognitoRequest);
		log.info("Signup Is Completed!!!");
		log.info("Cognito Data Preserve Started");
		cognitoUserService.preserveCognitoInfo(response);
		log.info("Cognito Data Preserve Completed!!!");
		log.info("CognitoHandler.handleRequest()-");
		return response;
	}
}
