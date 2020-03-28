package com.mktx.cognito.lambda;

import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mktx.cognito.exception.CustomException;
import com.mktx.cognito.model.AuthenticateRequest;
import com.mktx.cognito.security.model.SpringSecurityUser;
import com.mktx.cognito.service.CognitoServices;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.context.annotation.Import;

import java.net.MalformedURLException;
import java.text.ParseException;

@Slf4j
@Import(CognitoServices.class)
public class LoginHandler implements RequestHandler<AuthenticateRequest, SpringSecurityUser> {

    public static final String AUD = "aud";
    public static final String EVENTID = "event_id";
    public static final String TOKENUSE = "token_use";
    public static final String AUTHTIME = "auth_time";
    public static final String NAME = "name";
    public static final String PHONENUMBER = "phone_number";
    public static final String EXP = "exp";
    public static final String IAT = "iat";
    public static final String FAMILY = "family_name";
    public static final String EMAIL = "email";


    AdminInitiateAuthResult result = null;
    SpringSecurityUser user = null;

    @Override
    public SpringSecurityUser handleRequest(AuthenticateRequest authenticateRequest, Context context) {
        CognitoServices cognitoServices = new CognitoServices();
        JSONObject jsonObject=null;
        if(authenticateRequest.getMfaCode() == null) {
            result = cognitoServices.signIn(authenticateRequest);
            log.info("############# {}", result);
            user = new SpringSecurityUser();
            user.setVerified(true);
            user.setMessage("Please proceed with the MFA code you received");
        } else {
            AuthenticationResultType verify = cognitoServices.verify(authenticateRequest, result);
            user.setIdToken(verify.getIdToken());
            user.setMessage("MFA verified successfully");
            try {
                jsonObject= cognitoServices.verifyTheToken(user.getIdToken());
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (BadJOSEException ex) {
                ex.printStackTrace();
            } catch (CustomException ex) {
                ex.printStackTrace();
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (JOSEException ex) {
                ex.printStackTrace();
            }
            user.setAud(jsonObject.get(AUD).toString());
            user.setEventId(jsonObject.get(EVENTID).toString());
            user.setTokenUse(jsonObject.get(TOKENUSE).toString());
            user.setAuthTime(jsonObject.get(AUTHTIME).toString());
            user.setName(jsonObject.get(NAME).toString());
            user.setPhoneNumber(jsonObject.get(PHONENUMBER).toString());
            user.setExp(jsonObject.get(EXP).toString());
            user.setIat(jsonObject.get(IAT).toString());
            user.setFamilyName(jsonObject.get(FAMILY).toString());
            user.setEmail(jsonObject.get(EMAIL).toString());
        }
        return user;
    }


}
