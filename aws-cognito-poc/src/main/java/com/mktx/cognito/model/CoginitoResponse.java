package com.mktx.cognito.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoginitoResponse {
    private String username;
    private String userCreatedDate;
    private String lastModifiedDate;
    private Boolean enabled;
    private String userStatus;
    private String password;
    private String email;
    private String firm;

}
