package com.axis.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.axis.config.DynamodbConfig;
import com.axis.dao.AgentDao;
import com.axis.model.Agent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handler for requests to Lambda function.
 */
public class ThirdPartyApiCallerHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private AgentDao agentDao = new AgentDao(DynamodbConfig.dynamoDBMapper());
    private ObjectMapper objectMapper = new ObjectMapper();

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        headers.put("Access-Control-Allow-Headers", "*");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "*");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        response.setHeaders(headers);
        System.out.println("request:- " + input);
        if (input.getPath().equals("/agents/fetchfromhrms") && input.getHttpMethod().equals("GET")) {
            HttpURLConnection httpURLConnection = null;
            try {
                URL url = new URL("https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/mock/agents");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                StringBuffer bufferedBody = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    bufferedBody.append(line);
                }
                bufferedReader.close();
                /*HttpClient httpClient = HttpClient.newHttpClient();
                HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/mock/agents")).build();
                HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());*/
                String body = bufferedBody.toString();
                System.out.println("body:- " + body);
                List<Map<String, String>> list = objectMapper.readValue(body, new TypeReference<List<Map<String, String>>>() {
                });
                List<Agent> fetchedAgentList = new ArrayList<>();
                list.forEach(
                        obj -> {
                            Agent agent = new Agent();
                            agent.setEmail(obj.get("email"));
                            agent.setName(obj.get("name"));
                            agent.setDob(obj.get("dob") == null ? null : new Date(Long.parseLong(obj.get("dob"))));
                            agent.setAddress(obj.get("address"));
                            agent.setCrmId(obj.get("crmId"));
                            agent.setTeleCallingId(obj.get("teleCallingId"));
                            agent.setLicenseUrnNo(obj.get("licenseUrnNo"));
                            agent.setLicenseIssueDate(obj.get("licenseIssueDate") == null ? null : new Date(Long.parseLong(obj.get("licenseIssueDate"))));
                            agent.setLicenseExpiryDate(obj.get("licenseExpiryDate") == null ? null : new Date(Long.parseLong(obj.get("licenseExpiryDate"))));
                            agent.setUserId(obj.get("userId"));
                            System.out.println(agent);
                            fetchedAgentList.add(agent);
                        }
                );
                for (Agent agentObj :
                        fetchedAgentList) {
                    System.out.println(agentObj);
                    agentDao.saveAgent(agentObj);
                }

//                httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
//                        .thenApply(
//                                HttpResponse::body
//                        ).thenAccept(System.out::println).join();
                response.setBody("{}");
                return response;
            } catch (JsonProcessingException e) {
                response.setBody("Error Occured");
                response.setStatusCode(500);
                return response;
            } catch (IOException e) {
                response.setBody("Error Occured");
                response.setStatusCode(500);
                return response;
            } finally {
                httpURLConnection.disconnect();
            }
        } else if (input.getPath().equals("/agents/updatelicenseinformation") && input.getHttpMethod().equals("GET")) {
            HttpURLConnection httpURLConnection = null;
            try {
                URL url = new URL("https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/mock/agentsLicenseInformation");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                StringBuffer bufferedBody = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    bufferedBody.append(line);
                }
                bufferedReader.close();
                /*HttpClient httpClient = HttpClient.newHttpClient();
                HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/mock/agents")).build();
                HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());*/
                String body = bufferedBody.toString();
                List<Map<String, String>> list = objectMapper.readValue(body, new TypeReference<List<Map<String, String>>>() {
                });
                List<Agent> fetchedAgentList = new ArrayList<>();
                list.forEach(
                        obj -> {
                            Agent agent = new Agent();
                            agent.setEmail(obj.get("email"));
                            agent.setLicenseUrnNo(obj.get("licenseUrnNo"));
                            agent.setLicenseIssueDate(obj.get("licenseIssueDate") == null ? null : new Date(Long.parseLong(obj.get("licenseIssueDate"))));
                            agent.setLicenseExpiryDate(obj.get("licenseExpiryDate") == null ? null : new Date(Long.parseLong(obj.get("licenseExpiryDate"))));
                            fetchedAgentList.add(agent);
                        }
                );
                for (Agent agentObj :
                        fetchedAgentList) {
                    agentDao.updateAgentDetails(agentObj);
                }

//                httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
//                        .thenApply(
//                                HttpResponse::body
//                        ).thenAccept(System.out::println).join();
                response.setBody("{}");
                return response;
            } catch (JsonProcessingException e) {
                response.setBody("Error Occured");
                response.setStatusCode(500);
                return response;
            } catch (IOException e) {
                response.setBody("Error Occured");
                response.setStatusCode(500);
                return response;
            } finally {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }


}
