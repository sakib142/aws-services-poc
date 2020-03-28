package com.mktx.cognito.model;


import lombok.Data;

@Data
public class LogOutDTO {

    private String accessToken;
    private String username;
}
