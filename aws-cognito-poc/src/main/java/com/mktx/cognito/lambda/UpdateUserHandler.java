package com.mktx.cognito.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mktx.cognito.model.UpdateDTO;
import com.mktx.cognito.service.CognitoServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(CognitoServices.class)
public class UpdateUserHandler implements RequestHandler<UpdateDTO, Void> {

    @Override
    public Void handleRequest(UpdateDTO updateDTO, Context context) {
        CognitoServices cognitoServices = new CognitoServices();
        cognitoServices.updateUserAttributes(updateDTO.getUsername());
        //to disable the user
        cognitoServices.disableUser(updateDTO.getUsername());
        log.info("After disabling");
        //to enable the user
        //cognitoServices.enableUser(updateDTO.getUsername());
        log.info("After enabling");
        //to get the user details
        //return cognitoServices.updateDetails(updateDTO.getUsername());
        return null;
    }
}
