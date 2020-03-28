package com.mktx.cognito.client;

import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.mktx.cognito.model.AuthenticateRequest;
import com.mktx.cognito.model.CodeDTO;
import com.mktx.cognito.model.LogOutDTO;
import com.mktx.cognito.service.CognitoServices;
import net.minidev.json.JSONObject;

public class TestClient {

//    private static final String userName = "ems-dev";
    private static final String userName = "ems-dev6";
//    private static final String password = "Abcd_1234";
    private static final String password = "Pqrs_1234";
    private static final String userPoolID = "us-east-2_w1KRJa2UC";
    private static final String accessToken = "eyJraWQiOiJnb3p3RzNMUTgxOEsyZ0V4eldCQU1FVUdOSU1OSWgxdFlBUzNlYW5SalJZPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI3OTM0Y2E2YS0xOWE3LTRjYjUtYmQ5YS0zYzMwZWJjYzgxNTYiLCJldmVudF9pZCI6IjM3NThjM2ViLTViZGEtNGM1Mi04YTk0LThhNDAyYWVhYjIwZSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE1NjM5MDA0NjQsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTIuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0yX3cxS1JKYTJVQyIsImV4cCI6MTU2MzkwNDA2NCwiaWF0IjoxNTYzOTAwNDY0LCJqdGkiOiI3M2RiZmNmMS1hOWM0LTRjZmItOTQ3NS01MjM0Zjg4YzA3NmIiLCJjbGllbnRfaWQiOiI0cDRxMXRscHVkc28ybnFjZzQ4bzdmdTdhciIsInVzZXJuYW1lIjoiZW1zLWRldjYifQ.cLCTNOM0QLiUj4lx6fo7_1g127hsLY9YT2QRKp2KlJUOZxStnTYCn_DBdd6E0lOprnEVh-sxbFUmyh4jE0Af-PmMLZ2atTcVRsJT4w4I0r3F9ZoF_OLfNYhNfPqz9o1mQK1FFa-HReZloLvt1aAPjETmOrO3ICBCjdYjrF753Nsi-2iN7HRc8IKm5yIQCLezrsP8NLW4Ejo_ZrnQRsMS5Pd66tiyf-kL5AGdaAdNc6ApYvt0vJIMucjY9xtxhJFZbn4j8qRgMUjBRaKQ6hfsjOMsTUEhuKY6QCJk69cNND4-8lkU8FBYMnELrSf87yPT2ryTH9zL7WkpzJ2kerFxGA";

    public static void main(String[] args) {
//        verifyToken();
        login();
//        logEvents();
//        signOut();
//        adminSignOut();
//        isLoggedIn();
    }

    private static void isLoggedIn() {
        CognitoServices cognitoServices= new CognitoServices();
        CodeDTO codeDTO = new CodeDTO();
        codeDTO.setAccessCode(accessToken);
        System.out.println(cognitoServices.isLoggedIn(codeDTO));
    }

    public static void logEvents() {
        System.out.println("Logging event.....");
        CognitoServices cognitoServices = new CognitoServices();
        cognitoServices.findLoginHistory(userName, userPoolID);
    }

    public static void login() {
        CognitoServices cognitoServices = new CognitoServices();
        AuthenticateRequest req = new AuthenticateRequest();
        req.setUsername(userName);
        req.setPassword(password);

        AdminInitiateAuthResult result = cognitoServices.signIn(req);
        System.out.println(result.getAuthenticationResult().getAccessToken());

    }

    public static void signOut() {
        CognitoServices cognitoServices = new CognitoServices();

        LogOutDTO dto = new LogOutDTO();
        dto.setUsername(userName);
        dto.setAccessToken(accessToken);
        cognitoServices.signOut(dto);
    }

    public static void adminSignOut(){
        CognitoServices cognitoServices = new CognitoServices();
        cognitoServices.forceLogOFF(userName, userPoolID);
    }

    public static void verifyToken() {
        CognitoServices cognitoServices = new CognitoServices();
        try {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            JSONObject jb = cognitoServices.verifyTheToken("eyJraWQiOiJyc01sVENxdnB6YUswVVpsMmdcLzR2Y21QakE3Yk81bWhJdHBjbjB0UndQUT0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI2ZTRjZmEyNS1lYzk3LTRmODgtYjljYi0xOGE5YmQ1OTIyN2QiLCJldmVudF9pZCI6IjVmOTE0NTA2LTI2YTQtNGYxMi05ZGVlLTFjYWIzYjIxN2IwMCIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE1NjE3MTg5NDMsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTIuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0yXzRFU0RvdTFFMyIsImV4cCI6MTU2MTcyMjU0MywiaWF0IjoxNTYxNzE4OTQzLCJqdGkiOiJhMGYxYmNjOS0zZWY4LTQwMTMtYjc0Yy02M2Y2ZGZhOTcyNjgiLCJjbGllbnRfaWQiOiIxMTQxbzdtY2dnaHI5ZmM4aTJpbjh0amdrOSIsInVzZXJuYW1lIjoiZW1zLWRldiJ9.IUoI867qhDuPpuXndVtXJvU7ZNBLdbFfP8b1z_zv06tZcpPKh_VjYmuk-08TsVg2Hp3VIB72ShEKNOY8qwvN8OsisDaHpW1Es6g7YOkcERaU-gfPtZsv37_N0r4j9BlmhPf9B6Uu1mU7Id8V45uHFbMEL5I0f1TUWG62ri2PJ-XF9Sq-BGZNRcHiH1q5qiF_Daf1L6RN6pH_c9qjJvfzN121jncRLSFk_lT_lSh7-oK6ZPQ2QHX32ah17kgjAAifRMdddr37zSg8QnqK1YrgDidXjvWepTelE-JNP7IQ5y190B6KP8RK-oLhuf6D0fgNdsL0Kn3DNMvm0zasu72GRA");
            System.out.println(jb);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
