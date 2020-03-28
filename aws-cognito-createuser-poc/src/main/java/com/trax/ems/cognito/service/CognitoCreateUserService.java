package com.trax.ems.cognito.service;

import java.util.List;

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
import com.amazonaws.util.StringUtils;
import com.trax.ems.cognito.client.CongnitoClient;
import com.trax.ems.cognito.exception.CognitoException;
import com.trax.ems.cognito.model.CognitoUser;
import com.trax.ems.cognito.utils.AwsDynamoDBEncryptor;
import com.trax.ems.cognito.utils.StringValidationHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CognitoCreateUserService {

	@Autowired
	AwsDynamoDBEncryptor awsDynamoDBEncryptor;

	@Value("${mktx.userpool}")
	String userPoolId;

	@Autowired
	CongnitoClient cognitoBuilder;

	private AWSCognitoIdentityProvider getAWSCognitoIdentityProvider() {
		return cognitoBuilder.getAWSCognitoIdentityClient();
	}

	public CognitoUser readCognitoData(List<String> partitionKeys) {
		log.info("CognitoReadService.readCognitoData()+");
		CognitoUser user = awsDynamoDBEncryptor.decryptRecord(partitionKeys);
		log.info("CognitoReadService.readCognitoData()-");
		return user;
	}

	public CognitoUser signUp(CognitoUser userSignUpRequest) throws CognitoException {
		AWSCognitoIdentityProvider congnitoClient = getAWSCognitoIdentityProvider();

		if (StringUtils.isNullOrEmpty(userSignUpRequest.getUsername())) {
			userSignUpRequest.setEmail(userSignUpRequest.getEmail());
		}

		log.info("Email is " + userSignUpRequest.getEmail());
		log.info("userPoolId: " + userPoolId);
		try {

			AdminCreateUserRequest cognitoRequest = new AdminCreateUserRequest().withUserPoolId(userPoolId)
					.withUsername(userSignUpRequest.getUsername())
					.withUserAttributes(new AttributeType().withName("email").withValue(userSignUpRequest.getEmail()),
							new AttributeType().withName("name").withValue(userSignUpRequest.getName()),
							new AttributeType().withName("family_name").withValue(userSignUpRequest.getFirm()),
							new AttributeType().withName("phone_number").withValue(userSignUpRequest.getPhonenumber()),
							new AttributeType().withName("email_verified").withValue("true"),
							new AttributeType().withName("phone_number_verified").withValue("true"))
					.withDesiredDeliveryMediums(DeliveryMediumType.EMAIL).withForceAliasCreation(Boolean.FALSE);

			AdminCreateUserResult cognitoResult = congnitoClient.adminCreateUser(cognitoRequest);
			CognitoUser userResult = new CognitoUser();
			UserType cognitoUser = cognitoResult.getUser();
			userResult.setEmail(userSignUpRequest.getEmail());
			userResult.setUsername(userSignUpRequest.getUsername());
			userResult.setEnabled(cognitoUser.getEnabled());
			userResult.setUserStatus(cognitoUser.getUserStatus());
			userResult.setLastModifiedDate(
					StringValidationHelper.convertDateToString(cognitoUser.getUserLastModifiedDate(), "MM-dd-yyyy"));
			userResult.setUserCreatedDate(
					StringValidationHelper.convertDateToString(cognitoUser.getUserCreateDate(), "MM-dd-yyyy"));
			userResult.setFirm(userSignUpRequest.getFirm());
			log.info("User SignUp Result" + userResult);

			return userResult;
		} catch (AWSCognitoIdentityProviderException awsexception) {
			log.info(awsexception.getMessage());
			throw new CognitoException(awsexception.getMessage(), awsexception.getErrorCode(),
					awsexception.getMessage() + awsexception.getErrorCode());

		} catch (Exception e) {
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE, e.getMessage());
		}
	}

}
