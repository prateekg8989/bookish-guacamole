package com.axis.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.axis.config.SesConfig;
import com.axis.model.Agent;
import com.axis.model.User;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.SesClientBuilder;
import software.amazon.awssdk.services.ses.model.*;

public class UserDao {

    private DynamoDBMapper dynamoDBMapper;

    public UserDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void saveUser(User user) {
        user.setIsDisable(false);
        this.dynamoDBMapper.save(user);
        if (user.getRole().equals("agent")) {
            Agent savedAgent = this.dynamoDBMapper.load(Agent.class, user.getEmail());
            if (savedAgent == null) {
                Agent agent = new Agent();
                agent.setUserId(user.getUserId());
                agent.setEmail(user.getEmail());
                agent.setIsDisable(false);
                this.dynamoDBMapper.save(agent);
            }
            VerifyEmailIdentityRequest verifyEmailIdentityRequest = VerifyEmailIdentityRequest.builder().emailAddress(user.getEmail()).build();
            SesClient sesClient = SesConfig.getSesClient();
            sesClient.verifyEmailIdentity(verifyEmailIdentityRequest);

        }
    }
}
