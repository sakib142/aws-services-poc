package com.mktx.cognito.lambda;

import java.util.List;

import com.amazonaws.services.cognitoidp.model.UserType;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mktx.cognito.model.SearchByFirmReq;
import com.mktx.cognito.service.CognitoServices;

public class SearchByFirmCognito implements RequestHandler<SearchByFirmReq,  List<UserType>> {

    @Override
    public List<UserType> handleRequest(SearchByFirmReq serchReq, Context context) {
        CognitoServices cognitoServices = new CognitoServices();
        return cognitoServices.numberofAuthenticatedUserWithFirm(serchReq);
    }
}
