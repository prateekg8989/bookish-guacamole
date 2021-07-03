package com.axis.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.axis.service.AgentService;

/**
 * Handler for requests to Lambda function.
 */
public class RemindingServiceHandler implements RequestHandler<ScheduledEvent, String> {

    private AgentService agentService;

    public String handleRequest(final ScheduledEvent input, final Context context) {
        System.out.println("License reminding service called:- " + input);
        agentService = new AgentService();
        agentService.sendEmails();
        return null;
    }

}
