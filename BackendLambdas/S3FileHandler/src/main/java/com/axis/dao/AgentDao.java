package com.axis.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.axis.model.Agent;
import com.axis.model.User;

public class AgentDao {

    private DynamoDBMapper dynamoDBMapper;

    public AgentDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Agent updateAgentDetails(Agent agent) {
        Agent savedAgent = this.dynamoDBMapper.load(Agent.class, agent.getEmail());
        if(savedAgent == null ){
            agent.setIsDisable(false);
            this.dynamoDBMapper.save(agent);
            return agent;
        } else {
            if(agent.getUserId() != null) {
                savedAgent.setUserId(agent.getUserId());
            }
            if(agent.getName() != null) {
                savedAgent.setName(agent.getName());
            }
            if(agent.getAddress() != null) {
                savedAgent.setAddress(agent.getAddress());
            }
            if(agent.getDob() != null) {
                savedAgent.setDob(agent.getDob());
            }
            if(agent.getTeleCallingId() != null) {
                savedAgent.setTeleCallingId(agent.getTeleCallingId());
            }
            if(agent.getLicenseUrnNo() != null) {
                savedAgent.setLicenseUrnNo(agent.getLicenseUrnNo());
            }
            if(agent.getLicenseIssueDate() != null) {
                savedAgent.setLicenseIssueDate(agent.getLicenseIssueDate());
            }
            if(agent.getLicenseExpiryDate() != null) {
                savedAgent.setLicenseExpiryDate(agent.getLicenseExpiryDate());
            }
            this.dynamoDBMapper.save(savedAgent);
            return savedAgent;
        }

    }
}
