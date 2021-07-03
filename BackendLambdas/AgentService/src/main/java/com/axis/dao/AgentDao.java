package com.axis.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.axis.model.Agent;
import com.axis.model.User;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AgentDao {

    private DynamoDBMapper dynamoDBMapper;

    public AgentDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<Agent> getListOfAgents() {
        HashMap<String, AttributeValue> mapVal = new HashMap<String, AttributeValue>();
        mapVal.put(":val1", new AttributeValue().withBOOL(false));
        return this.dynamoDBMapper.scan(Agent.class, new DynamoDBScanExpression()
                .withFilterExpression("isDisable = :val1").withExpressionAttributeValues(mapVal));
//         this.dynamoDBMapper.scan(Agent.class, new DynamoDBScanExpression());
    }

    public Agent getAgentByEmail(String email) {
        Agent savedAgent = this.dynamoDBMapper.load(Agent.class, email);
        return savedAgent;
    }

    public Agent saveAgent(Agent agent) {
        Agent savedAgent = this.dynamoDBMapper.load(Agent.class, agent.getEmail());
        if (savedAgent == null) {
            List<AttributeType> listOfUserAttr = new ArrayList<>();
            listOfUserAttr.add(AttributeType.builder().name("email").value(agent.getEmail()).build());
            listOfUserAttr.add(AttributeType.builder().name("custom:role").value("agent").build());
            CognitoIdentityProviderClient cognitoIdentityProviderClient = getCognitoIdentityProviderClient();
            SignUpResponse signUpResponse = cognitoIdentityProviderClient.signUp(SignUpRequest.builder().clientId(System.getenv("CLIENT_ID")).username(agent.getEmail()).password(agent.getEmail()).userAttributes(listOfUserAttr).build());
            agent.setUserId(signUpResponse.userSub());
        }
        return updateAgentDetails(agent);
    }

    public Agent updateAgentDetails(Agent agent) {
        System.out.println("pppppppppppp-> " + agent);
        Agent savedAgent = this.dynamoDBMapper.load(Agent.class, agent.getEmail());
        if (savedAgent == null) {
            this.dynamoDBMapper.save(agent);
            return agent;
        } else {
            if (agent.getUserId() != null) {
                savedAgent.setUserId(agent.getUserId());
            }
            if (agent.getEmail() != null) {
                savedAgent.setEmail(agent.getEmail());
            }
            if (agent.getName() != null) {
                savedAgent.setName(agent.getName());
            }
            if (agent.getAddress() != null) {
                savedAgent.setAddress(agent.getAddress());
            }
            if (agent.getDob() != null) {
                savedAgent.setDob(agent.getDob());
            }
            if (agent.getTeleCallingId() != null) {
                savedAgent.setTeleCallingId(agent.getTeleCallingId());
            }
            if (agent.getLicenseUrnNo() != null) {
                savedAgent.setLicenseUrnNo(agent.getLicenseUrnNo());
            }
            if (agent.getLicenseIssueDate() != null) {
                savedAgent.setLicenseIssueDate(agent.getLicenseIssueDate());
            }
            if (agent.getLicenseExpiryDate() != null) {
                savedAgent.setLicenseExpiryDate(agent.getLicenseExpiryDate());
            }
            this.dynamoDBMapper.save(savedAgent);
            return savedAgent;
        }
    }

    public void deleteAgent(String email) {
        CognitoIdentityProviderClient cognitoIdentityProviderClient = getCognitoIdentityProviderClient();
        AdminDeleteUserResponse adminDeleteUserResponse = cognitoIdentityProviderClient.adminDeleteUser(AdminDeleteUserRequest.builder().userPoolId(System.getenv("USER_POOL_ID")).username(email).build());
        System.out.println("adminDeleteUserResponse:-  " + adminDeleteUserResponse.toString());
        Agent agent1 = new Agent();
        agent1.setEmail(email);
        Agent agent = dynamoDBMapper.load(agent1);
        User user1 = new User();
        user1.setUserId(agent.getUserId());
        dynamoDBMapper.delete(user1);
        dynamoDBMapper.delete(agent1);
    }

    public void disableAgent(String email) {
        CognitoIdentityProviderClient cognitoIdentityProviderClient = getCognitoIdentityProviderClient();
        cognitoIdentityProviderClient.adminDisableUser(AdminDisableUserRequest.builder().userPoolId(System.getenv("USER_POOL_ID")).username(email).build());
        Agent agent1 = new Agent();
        agent1.setEmail(email);
        Agent agent = dynamoDBMapper.load(agent1);
        User user1 = new User();
        user1.setUserId(agent.getUserId());
        User user = dynamoDBMapper.load(user1);
        agent.setIsDisable(true);
        user.setIsDisable(true);
        dynamoDBMapper.save(agent);
        dynamoDBMapper.save(user);
    }

    CognitoIdentityProviderClient getCognitoIdentityProviderClient() {
        return CognitoIdentityProviderClient.builder().credentialsProvider(EnvironmentVariableCredentialsProvider.create()).region(Region.US_EAST_2).httpClientBuilder(UrlConnectionHttpClient.builder()).build();
    }
}
