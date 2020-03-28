package com.mktx.cognito.client;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.stereotype.Component;


@Component
public class CongnitoClient {



    private AWSCognitoIdentityProvider cognitoIdentityProvider;
    private ClasspathPropertiesFileCredentialsProvider propertiesFileCredentialsProvider;
    private String region="us-east-2";




    private void initCommonInfo() {
        if(null == propertiesFileCredentialsProvider) {
            propertiesFileCredentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
        }
    }


    public AWSCognitoIdentityProvider getAWSCognitoIdentityClient() {
        if( null == cognitoIdentityProvider) {
            initCommonInfo();

            cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.standard()
                    .withCredentials(propertiesFileCredentialsProvider)
                    .withRegion(region)
                    .build();
        }

        return cognitoIdentityProvider;
    }
}
