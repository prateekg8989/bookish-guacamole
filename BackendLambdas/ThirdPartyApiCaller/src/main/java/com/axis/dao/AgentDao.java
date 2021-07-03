package com.axis.dao;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.axis.model.Agent;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;

import java.util.ArrayList;
import java.util.List;

public class AgentDao {

    private DynamoDBMapper dynamoDBMapper;

    public AgentDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<Agent> getListOfAgents() {
        return this.dynamoDBMapper.scan(Agent.class, new DynamoDBScanExpression());
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
            return updateAgentDetails(agent);
        }

        return null;
    }

    public Agent updateAgentDetails(Agent agent) {
        Agent savedAgent = this.dynamoDBMapper.load(Agent.class, agent.getEmail());
        System.out.println();
        if (savedAgent == null) {
            agent.setIsDisable(false);
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

    static CognitoIdentityProviderClient getCognitoIdentityProviderClient() {
        return  CognitoIdentityProviderClient.builder().credentialsProvider(EnvironmentVariableCredentialsProvider.create()).region(Region.US_EAST_2).httpClientBuilder(UrlConnectionHttpClient.builder()).build();
    }
}
