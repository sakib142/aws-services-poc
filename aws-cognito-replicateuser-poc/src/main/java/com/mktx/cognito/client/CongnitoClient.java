package com.mktx.cognito.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

@Component
public class CongnitoClient {

	private AWSCognitoIdentityProvider cognitoIdentityProvider;
	private ClasspathPropertiesFileCredentialsProvider propertiesFileCredentialsProvider;

	@Value("${amazon.aws.region}")
	private String region;

	private void initCommonInfo() {
		if (null == propertiesFileCredentialsProvider) {
			propertiesFileCredentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
		}
	}

	public AWSCognitoIdentityProvider getAWSCognitoIdentityClient() {
		if (null == cognitoIdentityProvider) {
			initCommonInfo();

			cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.standard()
					.withCredentials(propertiesFileCredentialsProvider).withRegion(region).build();
		}

		return cognitoIdentityProvider;
	}
}
