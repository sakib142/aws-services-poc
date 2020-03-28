package com.mktx.ems.lambdaauth.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mktx.ems.lambdaauth.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Objects;

public class AwsCognitoJwtParserUtil {

    public static final String NOT_VALID_JSON_WEB_TOKEN = "Not a valid json web token";
    public static final String TOKEN_EXPIRED = " Aws Jwt Token has expired.";

    public static final String ISS = "iss";
    public static final String JWK_URl_SUFFIX = "/.well-known/jwks.json";
    public static final String JWK_FILE_PREFIX = "/jwk_";
    public static final String JWK_FILE_SUFFIX = ".json";

    private static final int HEADER = 0;
    private static final int PAYLOAD = 1;
    private static final int SIGNATURE = 2;
    private static final int JWT_PARTS = 3;

    public static JsonObject getHeader(String jwt) throws CustomException {
        try {
            validateJWT(jwt);
            String header = jwt.split("\\.")[HEADER];
            final byte [] headerBytes = Base64.getUrlDecoder().decode(header);
            final String headerString = new String(headerBytes, "UTF-8");
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(headerString);
            return jsonObject;
        }catch (UnsupportedEncodingException e){
            throw new CustomException(HttpStatus.UNAUTHORIZED, NOT_VALID_JSON_WEB_TOKEN, jwt);
        }


    }

    /**
     * Returns payload of a JWT as a JSON object.
     *
     * @param jwt   Required valid JSON Web Token as String.
     * @return AWS jwt payload as a JsonObject.
     */
    public static JsonObject getPayload(String jwt) throws CustomException {
        try {
            validateJWT(jwt);
            final String payload = jwt.split("\\.")[PAYLOAD];
            final byte[] payloadBytes =  Base64.getUrlDecoder().decode(payload);
            final String payloadString = new String(payloadBytes, "UTF-8");
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(payloadString);
            return jsonObject;
        } catch ( UnsupportedEncodingException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, NOT_VALID_JSON_WEB_TOKEN, jwt);
        }
    }



    /**
     * Returns signature of a JWT as a String.
     *
     * @param jwt  Required valid JSON Web Token as String.
     * @return AWS JWT signature as a String.
     */
    public static String getSignature(String jwt) throws CustomException {
        try {
            validateJWT(jwt);
            final String signature = jwt.split("\\.")[SIGNATURE];
            final byte[] signatureBytes = Base64.getUrlDecoder().decode(signature);
            return new String(signatureBytes, "UTF-8");
        } catch (final Exception e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, NOT_VALID_JSON_WEB_TOKEN, jwt);
        }
    }

    /**
     * Returns a claim, from the {@code JWT}s' payload, as a String.
     *
     * @param jwt Required valid JSON Web Token as String.
     * @param claim  Required claim name as String.
     * @return  claim from the JWT as a String.
     */
    public static String getClaim(String jwt, String claim) throws CustomException {
        try {
            final JsonObject payload = getPayload(jwt);
            final Object claimValue = payload.get(claim);

            if (claimValue != null) {
                return claimValue.toString();
            }

        } catch (final Exception e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, NOT_VALID_JSON_WEB_TOKEN, jwt);
        }
        return null;
    }

    /**
     * Checks if {@code JWT} is a valid JSON Web Token.
     *
     * @param jwt
     */
    public static void validateJWT(String jwt) throws CustomException {
        // Check if the the JWT has the three parts
        final String[] jwtParts = jwt.split("\\.");
        if (jwtParts.length != JWT_PARTS) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, NOT_VALID_JSON_WEB_TOKEN, jwt);
        }
    }

    public static String getJsonWebKeyURL(String token) throws CustomException {

        JsonObject payload = AwsCognitoJwtParserUtil.getPayload(token);
        JsonElement issJsonElement = payload.get(ISS);
        if (Objects.isNull(issJsonElement)) {
            throw  new CustomException(HttpStatus.UNAUTHORIZED, NOT_VALID_JSON_WEB_TOKEN, payload.toString());
        }

        String issString = issJsonElement.getAsString();
        String jwkURl = issString + JWK_URl_SUFFIX;

        return jwkURl;
    }


    /**
     * Get the user pool from the iss url.
     * @param issUrl
     * @return ISS - token issuer URL.
     */
    private static String getUserPoolFromPayload(String issUrl) {

        String [] issArray = issUrl.split("amazonaws.com/");
        return issArray[1];
    }


}
