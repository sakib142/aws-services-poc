package com.mktx.cognito.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.mktx.cognito.authentication.AwsCognitoJwtValidatorUtil;
import com.mktx.cognito.client.CongnitoClient;
import com.mktx.cognito.exception.CognitoException;
import com.mktx.cognito.exception.CustomException;
import com.mktx.cognito.model.*;
import com.mktx.cognito.util.StringValidationHelper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.*;

@Service
@Slf4j
@Import({ CongnitoClient.class })
public class CognitoServices {

	private String userPoolId = "us-east-2_w1KRJa2UC";

	private String clientId = "4p4q1tlpudso2nqcg48o7fu7ar";

	private String region = "us-east-2";

	private static final String SECRET_HASH = "SECRET_HASH";

	private String groupName = "NewSampleCognito";

	CongnitoClient cognitoBuilder = new CongnitoClient();

	/**
	 * New password required challenge key
	 */
	private static final String NEW_PASS_WORD_REQUIRED = "NEW_PASSWORD_REQUIRED";

	private static final String SMS_MFA = "SMS_MFA";

	/**
	 * Password key
	 */
	private static final String PASS_WORD = "PASSWORD";
	/**
	 * Username key
	 */
	private static final String USERNAME = "USERNAME";
	/**
	 * New password key
	 */
	private static final String NEW_PASSWORD = "NEW_PASSWORD";

	private static final String SMS_MFA_CODE = "SMS_MFA_CODE";

	private static final String CODE_DELIVERY_DELIVERY_MEDIUM = "CODE_DELIVERY_DELIVERY_MEDIUM";

	private static final String CODE_DELIVERY_DESTINATION = "CODE_DELIVERY_DESTINATION";

	private static final String USER_ID_FOR_SRP = "USER_ID_FOR_SRP";

	private AWSCognitoIdentityProvider getAWSCognitoIdentityProvider() {
		return cognitoBuilder.getAWSCognitoIdentityClient();
	}

	public CoginitoResponse signUp(CognitoRequest userSignUpRequest) {
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
			CoginitoResponse userResult = new CoginitoResponse();
			UserType cognitoUser = cognitoResult.getUser();
			userResult.setEmail(userSignUpRequest.getEmail());
			userResult.setUsername(userSignUpRequest.getUsername());
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

	public void signUpConfirmation(CognitoRequest signUpRequest) {
		String temporaryPassword = signUpRequest.getTempPassword();

		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();

		if (StringUtils.isBlank(signUpRequest.getEmail())) {
			throw new CognitoException("Invalid email", CognitoException.EMAIL_MISSING_EXCEPTION, "Invalid email");
		}

		if (StringUtils.isBlank(signUpRequest.getPassword())) {
			throw new CognitoException("Invalid Password", CognitoException.INVALID_PASS_WORD_EXCEPTION,
					"Invalid password");
		}

		try {

			// First Attempt must attempt signin with temporary password in order to
			// establish session for password change
			Map<String, String> initialParams = new HashMap<>();
			initialParams.put(USERNAME, signUpRequest.getUsername());
			initialParams.put(PASS_WORD, temporaryPassword);

			// Initializes the request.
			AdminInitiateAuthRequest initialRequest = new AdminInitiateAuthRequest()
					.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH).withAuthParameters(initialParams)
					.withClientId(clientId).withUserPoolId(userPoolId);

			// Invokes the cognito authentication
			AdminInitiateAuthResult initialResponse = cognitoClient.adminInitiateAuth(initialRequest);

			// Validates if it has a new password.
			if (!ChallengeNameType.NEW_PASSWORD_REQUIRED.name().equals(initialResponse.getChallengeName())) {
				throw new CognitoException(initialResponse.getChallengeName(),
						CognitoException.USER_MUST_DO_ANOTHER_CHALLENGE, initialResponse.getChallengeName());
			}

			Map<String, String> challengeResponses = new HashMap<>();
			challengeResponses.put(USERNAME, signUpRequest.getUsername());
			challengeResponses.put(PASS_WORD, temporaryPassword);
			challengeResponses.put(NEW_PASSWORD, signUpRequest.getPassword());

			// Initializes the challenge response
			AdminRespondToAuthChallengeRequest finalRequest = new AdminRespondToAuthChallengeRequest()
					.withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
					.withChallengeResponses(challengeResponses).withClientId(clientId).withUserPoolId(userPoolId)
					.withSession(initialResponse.getSession());

			// Invokes the cognito authentication
			AdminRespondToAuthChallengeResult challengeResponse = cognitoClient
					.adminRespondToAuthChallenge(finalRequest);

			// Validates if it has another challenge
			if (!StringUtils.isBlank(challengeResponse.getChallengeName())) {
				throw new CognitoException(challengeResponse.getChallengeName(),
						CognitoException.USER_MUST_DO_ANOTHER_CHALLENGE, challengeResponse.getChallengeName());
			}

			log.info("Sign up confirm successfully for user {} ", signUpRequest.getUsername());
		} catch (com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException e) {

			throw new CognitoException(e.getMessage(), e.getErrorCode(), e.getMessage() + e.getErrorCode());
		} catch (CognitoException cognitoException) {
			throw cognitoException;
		} catch (Exception e) {

			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE, e.getMessage());
		}

	}

	public Boolean isLoggedIn(CodeDTO codeDTO) {
		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();
		GetUserRequest getUserRequest = new GetUserRequest();
		getUserRequest.withAccessToken(codeDTO.getAccessCode());
		GetUserResult result = cognitoClient.getUser(getUserRequest);
		if (result != null) {
			return true;
		} else {
			return false;
		}

	}

	public String signOut(LogOutDTO logOutDTO) {
		String message = null;
		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();
		try {
			if (logOutDTO.getAccessToken() != null) {
				GlobalSignOutRequest globalSignOutRequest = new GlobalSignOutRequest();
				globalSignOutRequest.withAccessToken(logOutDTO.getAccessToken());
				GlobalSignOutResult globalSignOutResult = cognitoClient.globalSignOut(globalSignOutRequest);
				message = "User Logout Success";
				log.info("User signed out: ", logOutDTO.getUsername());
				return message;
			} else {
				throw new CognitoException("Missing access token", CognitoException.ACCESS_TOKEN_MISSING_EXCEPTION, "Missing access token");
			}

		} catch (com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException e) {
			throw new CognitoException(e.getMessage(), e.getErrorCode(), e.getMessage() + e.getErrorCode());
		} catch (CognitoException cognitoException) {
			throw cognitoException;
		} catch (Exception e) {
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE, e.getMessage());
		}

	}

	public AdminInitiateAuthResult signIn(AuthenticateRequest authenticateRequest) {
		AdminInitiateAuthResult result = null;
		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();

		try {
			final Map<String, String> authParams = new HashMap<>();
			authParams.put(USERNAME, authenticateRequest.getUsername());
			authParams.put(PASS_WORD, authenticateRequest.getPassword());

			String sessionResult = null;
			final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
			authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH).withClientId(clientId).withUserPoolId(userPoolId)
					.withAuthParameters(authParams);

			result = cognitoClient.adminInitiateAuth(authRequest);

		} catch (AWSCognitoIdentityProviderException awse) {
			throw new CognitoException(awse.getMessage(), awse.getErrorCode(), awse.getMessage() + awse.getErrorCode());

		} catch (CognitoException cognitoException) {
			throw cognitoException;
		} catch (Exception e) {
			throw new CognitoException(e.getMessage(), CognitoException.GENERIC_EXCEPTION_CODE, e.getMessage());
		}

		return result;
	}

	public JSONObject verifyTheToken(String token)
			throws MalformedURLException, BadJOSEException, CustomException, ParseException, JOSEException {

		JWTClaimsSet jwtClaimsSet = AwsCognitoJwtValidatorUtil.validateAWSJwtToken(token);
		return jwtClaimsSet.toJSONObject();

	}

	public AuthenticationResultType verify(AuthenticateRequest request, AdminInitiateAuthResult result) {
		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();
		if (SMS_MFA.equals(result.getChallengeName())) {

			final Map<String, String> challengesResponse = new HashMap<>();
			challengesResponse.put(USERNAME, request.getUsername());
			challengesResponse.put(PASS_WORD, request.getPassword());
			challengesResponse.put(SMS_MFA_CODE, request.getMfaCode());
			final AdminRespondToAuthChallengeRequest requestChallenge = new AdminRespondToAuthChallengeRequest();
			requestChallenge.withChallengeName(ChallengeNameType.SMS_MFA).withChallengeResponses(challengesResponse)
					.withClientId(clientId).withUserPoolId(userPoolId).withSession(result.getSession());

			AdminRespondToAuthChallengeResult requestChallengeResponse = cognitoClient
					.adminRespondToAuthChallenge(requestChallenge);
			return requestChallengeResponse.getAuthenticationResult();
		}
		return null;
	}

	public List<UserType> numberofAuthenticatedUser(String poolId) {
		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();
		ListUsersRequest listUsersRequest = new ListUsersRequest();
		listUsersRequest.withUserPoolId(poolId);
		ListUsersResult result = cognitoClient.listUsers(listUsersRequest);
		List<UserType> users = result.getUsers();
		return users;

	}

	public List<AuthEventType> findLoginHistory(String userName, String poolId) {
		if (poolId == null) {
			poolId = userPoolId;
		}
		log.info("Fetching out the event history of Username: {} & UserPoolID: {}", userName, poolId);
		AdminListUserAuthEventsRequest req = new AdminListUserAuthEventsRequest();
		req.setUsername(userName);
		req.setUserPoolId(poolId);

		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();
		AdminListUserAuthEventsResult result = cognitoClient.adminListUserAuthEvents(req);
		System.out.println("Result : {}" + result);

		List<AuthEventType> authEvents = result.getAuthEvents();
		List<AuthEventType> loginEvents = new ArrayList<>();
		for (AuthEventType event : authEvents) {

			if ("SignIn".equals(event.getEventType()) && "Pass".equals(event.getEventResponse())) {

				loginEvents.add(event);
			}
		}

		log.info("Auth Events: {}", authEvents);
		log.info("Login Events: {}", loginEvents);
		return loginEvents;
	}

	public void forceLogOFF(String userName, String userPoolId) {
		log.info("Force logoff of Username: {} & UserPoolID: {}", userName, userPoolId);
		AdminUserGlobalSignOutRequest adminSignOutRequest = new AdminUserGlobalSignOutRequest();
		adminSignOutRequest.setUsername(userName);
		adminSignOutRequest.setUserPoolId(userPoolId);
		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();
		cognitoClient.adminUserGlobalSignOut(adminSignOutRequest);
	}

    public List<UserType> numberofAuthenticatedUserWithFirm(SearchByFirmReq serchReq) {
        System.out.println("start call search --- ");
        System.out.println("input 1 " + serchReq.getPoolId() + "---  input  2 " + serchReq.getFirmName());
        AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();
        ListUsersRequest listUsersRequest= new ListUsersRequest();
        listUsersRequest.withUserPoolId(serchReq.getPoolId()).withFilter(" family_name = \""+serchReq.getFirmName()+"\"");
        ListUsersResult result= cognitoClient.listUsers(listUsersRequest);
        List<UserType> users = result.getUsers();
        return users;
    }


	public void updateUserAttributes(String userName){
		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();
		List<AttributeType> userAttributes= new ArrayList<>();
		AdminUpdateUserAttributesRequest adminUpdateUserAttributesRequest= new AdminUpdateUserAttributesRequest();
		adminUpdateUserAttributesRequest.withUsername(userName)
				.withUserPoolId(userPoolId)
				.withUserAttributes(
						new AttributeType().withName("name").withValue("EMSUSER5")
				);

		AdminUpdateUserAttributesResult result= cognitoClient.adminUpdateUserAttributes(adminUpdateUserAttributesRequest);




	}

	public void disableUser(String userName){
		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();
		AdminDisableUserRequest adminDisableUserRequest = new AdminDisableUserRequest();
		adminDisableUserRequest.withUsername(userName)
				.withUserPoolId(userPoolId);
		AdminDisableUserResult adminDisableUserResult = cognitoClient.adminDisableUser(adminDisableUserRequest);

	}

	public void enableUser(String userName){
		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();
		AdminEnableUserRequest enableUserRequest = new AdminEnableUserRequest();
		enableUserRequest.withUsername(userName)
				.withUserPoolId(userPoolId);
		AdminEnableUserResult enableUserResult= cognitoClient.adminEnableUser(enableUserRequest);
	}

	public AdminListUserAuthEventsResult updateDetails(String userName){
		AWSCognitoIdentityProvider cognitoClient = getAWSCognitoIdentityProvider();
		AdminListUserAuthEventsRequest adminListUserAuthEventsRequest = new AdminListUserAuthEventsRequest();
		adminListUserAuthEventsRequest.withUsername(userName)

				.withUserPoolId(userPoolId);

		AdminListUserAuthEventsResult userAuthEventsResult = cognitoClient.adminListUserAuthEvents(adminListUserAuthEventsRequest);
		return userAuthEventsResult;
	}

}
