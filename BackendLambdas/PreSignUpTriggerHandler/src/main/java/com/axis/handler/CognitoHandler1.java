package com.axis.handler;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


/**
 * Handler for requests to Lambda function.
 */
public class CognitoHandler1 implements RequestHandler<Map<String, Object>, Map<String, Object>> {


    public Map<String, Object> handleRequest(final Map<String, Object> input, final Context context) {
        if(input.get("triggerSource").equals("PreSignUp_SignUp")) {
            Map<String, Object> mapOfResponse = (Map<String, Object>) input.get("response");
            mapOfResponse.put("autoConfirmUser", true);
        }
        return input;

    }
}
