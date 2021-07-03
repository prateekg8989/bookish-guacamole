package com.axis.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.axis.config.DynamodbConfig;
import com.axis.dao.AgentDao;
import com.axis.model.Agent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handler for requests to Lambda function.
 */
public class AgentServiceHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private AgentDao agentDao = new AgentDao(DynamodbConfig.dynamoDBMapper());
    private ObjectMapper objectMapper = new ObjectMapper();
    Logger logger = Logger.getLogger(AgentServiceHandler.class.getName());

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        headers.put("Access-Control-Allow-Headers", "*");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "*");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        if(input.getResource().equals("/agents") && input.getHttpMethod().equals("GET")){
            try {
                response.setBody(objectMapper.writeValueAsString(agentDao.getListOfAgents()));
                return response;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                response.setBody("Error Occured");
                response.setStatusCode(500);
                return response;
            }
        } else if (input.getResource().equals("/agent") && input.getHttpMethod().equals("GET")) {
            String email  = input.getQueryStringParameters().get("email");
            try {
                response.setBody(objectMapper.writeValueAsString(this.agentDao.getAgentByEmail(email)));
                return response;
            } catch (JsonProcessingException e) {
                logger.info("ERROR:- " + e.getMessage());
                response.setStatusCode(500);
                response.setBody("Error Occured");
                return response;
            }
        } else if (input.getResource().equals("/agent") && input.getHttpMethod().equals("POST")) {
            try {
                Agent agent = objectMapper.readValue(input.getBody(), Agent.class);
                response.setBody(objectMapper.writeValueAsString(this.agentDao.saveAgent(agent)));
                return response;
            } catch (JsonProcessingException e) {
                logger.severe("ERROR:- " + e.getMessage());
                response.setStatusCode(500);
                response.setBody("Error Occured");
                return response;
            }
        }else if (input.getResource().equals("/agent") && input.getHttpMethod().equals("PUT")) {
            try {
                Agent agent = objectMapper.readValue(input.getBody(), Agent.class);
                response.setBody(objectMapper.writeValueAsString(this.agentDao.updateAgentDetails(agent)));
                return response;
            } catch (JsonProcessingException e) {
                logger.info("ERROR:- " + e.getMessage());
                response.setStatusCode(500);
                response.setBody("Error Occured");
                return response;
            }
        } else if (input.getResource().equals("/agent") && input.getHttpMethod().equals("DELETE")) {
            String email  = input.getQueryStringParameters().get("email");
            this.agentDao.disableAgent(email);
            response.setBody("{'status': 'success', 'message':'The agent record is deleted'}");
            return response;
        }
        return null;
    }

}
