package com.mktx.cognito.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CognitoRequest {

    private String username;
    private String tempPassword;
    private String password;
    private String email;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String agreementFlag;
    private String userPoolId;
    private String firmName;
    private String idToken;
    private String accessToken;

}
