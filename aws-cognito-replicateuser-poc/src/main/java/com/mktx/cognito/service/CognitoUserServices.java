package com.mktx.cognito.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.DeliveryMediumType;
import com.amazonaws.services.cognitoidp.model.UserType;
import com.mktx.cognito.client.CongnitoClient;
import com.mktx.cognito.exception.CognitoException;
import com.mktx.cognito.model.CognitoRequest;
import com.mktx.cognito.model.CognitoUser;
import com.mktx.cognito.util.AwsDynamoDBEncryptor;
import com.mktx.cognito.util.StringValidationHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CognitoUserServices {

	@Value("${mktx.cognito.userpool}")
	private String userPoolId;

	@Value("${mktx.cognito.clientid}")
	private String clientId;

	@Autowired
	AwsDynamoDBEncryptor encryptor;

	@Autowired
	CongnitoClient cognitoBuilder;

	private AWSCognitoIdentityProvider getAWSCognitoIdentityProvider() {
		return cognitoBuilder.getAWSCognitoIdentityClient();
	}

	public CognitoUser signUp(CognitoRequest userSignUpRequest) {
		AWSCognitoIdentityProvider congnitoClient = getAWSCognitoIdentityProvider();

		if (StringUtils.isEmpty(userSignUpRequest.getUsername())) {
			userSignUpRequest.setEmail(userSignUpRequest.getEmail());
		}

		log.info("Email is " + userSignUpRequest.getEmail());
		try {

			AdminCreateUserRequest cognitoRequest = new AdminCreateUserRequest().withUserPoolId(userPoolId)
					.withUsername(userSignUpRequest.getUsername())
					.withUserAttributes(new AttributeType().withName("email").withValue(userSignUpRequest.getEmail()),
							new AttributeType().withName("name").withValue(userSignUpRequest.getName()),
							new AttributeType().withName("family_name").withValue(userSignUpRequest.getFirmName()),
							new AttributeType().withName("phone_number").withValue(userSignUpRequest.getPhoneNumber()),
							new AttributeType().withName("email_verified").withValue("true"),
							new AttributeType().withName("phone_number_verified").withValue("true"))
					.withDesiredDeliveryMediums(DeliveryMediumType.EMAIL).withForceAliasCreation(Boolean.FALSE);

			AdminCreateUserResult cognitoResult = congnitoClient.adminCreateUser(cognitoRequest);
			CognitoUser userResult = new CognitoUser();
			UserType cognitoUser = cognitoResult.getUser();
			userResult.setEmail(userSignUpRequest.getEmail());
			userResult.setUsername(userSignUpRequest.getUsername());
			userResult.setName(userSignUpRequest.getName());
			userResult.setFirm(userSignUpRequest.getFirmName());
			userResult.setPhonenumber(userSignUpRequest.getPhoneNumber());
			userResult.setEnabled(cognitoUser.getEnabled());
			userResult.setUserStatus(cognitoUser.getUserStatus());
			userResult.setLastModifiedDate(
					StringValidationHelper.convertDateToString(cognitoUser.getUserLastModifiedDate(), "MM-dd-yyyy"));
			userResult.setUserCreatedDate(
					StringValidationHelper.convertDateToString(cognitoUser.getUserCreateDate(), "MM-dd-yyyy"));
			log.info("User SignUp Result" + userResult);

			return userResult;
		} catch (AWSCognitoIdentityProviderException awsexception) {
			log.info(awsexception.getMessage());
			throw new CognitoException(awsexception.getMessage(), awsexception.getErrorCode(),
					awsexception.getMessage() + awsexception.getErrorCode());

		} catch (CognitoException cognitoException) {
			throw cognitoException;
		} catch (Exception e) {
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE, e.getMessage());
		}

	}

	public void preserveCognitoInfo(CognitoUser userResult) {
		log.info("CognitoServices.preserveCognitoInfo()+");
		encryptor.encryptRecord(userResult);
		log.info("CognitoServices.preserveCognitoInfo()-");
	}

}
