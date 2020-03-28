package com.mktx.cognito.model;

public class AuthenticateRequest {
    private String username;
    private String password;
    private String newPassword;
    private String accessToken;
    private String mfaCode;

    public String getEmail() {
        return email;
    }

    public AuthenticateRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMfaCode() {
        return mfaCode;
    }

    public AuthenticateRequest setMfaCode(String mfaCode) {
        this.mfaCode = mfaCode;
        return this;
    }
}
