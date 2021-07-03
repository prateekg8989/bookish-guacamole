package com.axis.handler;

import java.util.Map;
import java.util.logging.Logger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.axis.config.DynamodbConfig;
import com.axis.dao.UserDao;
import com.axis.model.User;

/**
 * Handler for requests to Lambda function.
 */
public class CognitoHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private DynamoDBMapper dynamoDBMapper;
    Logger logger = Logger.getLogger(CognitoHandler.class.getName());

    public Map<String, Object> handleRequest(final Map<String, Object> input, final Context context) {
        logger.info("Handler :- "  + input);
        if(input.get("triggerSource").equals("PostConfirmation_ConfirmSignUp")) {
            Map<String, Object> map = (Map<String, Object>) input.get("request");
            Map<String, Object> map2 = (Map<String, Object>) map.get("userAttributes");
            String userId = (String) map2.get("sub");
            String email = (String) map2.get("email");
            String role = (String) map2.get("custom:role");
            this.dynamoDBMapper = DynamodbConfig.dynamoDBMapper();
            UserDao userDao = new UserDao(this.dynamoDBMapper);
            userDao.saveUser(new User(userId, email,role));
        }
        return input;

    }
}
