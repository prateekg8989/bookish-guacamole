package com.axis.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.axis.config.DynamodbConfig;
import com.axis.dao.InsuranceTypeDao;
import com.axis.model.InsuranceType;
import com.axis.model.SoldInsurancePolicy;
import com.axis.model.Vendor;
import com.axis.service.InsuranceTypeService;
import com.axis.service.SoldInsurancePolicyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.codeguruprofilerjavaagent.LambdaProfiler;

/**
 * Handler for requests to Lambda function.
 */
public class InsuranceVendorServiceHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private SoldInsurancePolicyService soldInsurancePolicyService = new SoldInsurancePolicyService();
    private InsuranceTypeService insuranceTypeService = new InsuranceTypeService();
    private ObjectMapper objectMapper = new ObjectMapper();

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        return LambdaProfiler.profile(input, context, this::myHandlerFunction);
    }

    public APIGatewayProxyResponseEvent myHandlerFunction(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        headers.put("Access-Control-Allow-Headers", "*");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "*");


        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers).withStatusCode(200);
        if (input.getResource().equals("/policytypes")) {
            if (input.getHttpMethod().equals("GET")) {
                try {
                    response.setBody(objectMapper.writeValueAsString(insuranceTypeService.getListOfInsuranceType()));
                    return response;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    response.setBody("Error Occured");
                    response.setStatusCode(500);
                    return response;
                }
            } else if (input.getHttpMethod().equals("POST")) {
                try {
                    InsuranceType insuranceType = objectMapper.readValue(input.getBody(), InsuranceType.class);
                    insuranceTypeService.saveOrUpdateInsuranceType(insuranceType);
                    Map<String, String> mapOfResponse = new HashMap<String, String>();
                    mapOfResponse.put("status", "success");
                    mapOfResponse.put("message", "Insurance Type successfully added");
                    response.setBody(objectMapper.writeValueAsString(mapOfResponse));
                    response.setStatusCode(200);
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                    response.setBody("Error Occurred");
                    response.setStatusCode(500);
                    return response;
                }
            }
        } else if (input.getResource().equals("/vendor")) {
            if (input.getHttpMethod().equals("POST")) {
                try {
                    Map<String, String> map = input.getQueryStringParameters();
                    String insuranceTypeId = map.get("insuranceTypeId");
                    Vendor vendor = objectMapper.readValue(input.getBody(), Vendor.class);
                    insuranceTypeService.addVendorToInsuranceType(vendor, insuranceTypeId);
                    Map<String, String> mapOfResponse = new HashMap<String, String>();
                    mapOfResponse.put("status", "success");
                    mapOfResponse.put("message", "Vendor successfully added");
                    response.setBody(objectMapper.writeValueAsString(mapOfResponse));
                    response.setStatusCode(200);
                    return response;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    response.setBody("Error Occurred");
                    response.setStatusCode(500);
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                    response.setBody("Error Occurred");
                    response.setStatusCode(500);
                    return response;
                }
            }
        } else if (input.getResource().equals("/sold-policy")) {
            if (input.getHttpMethod().equals("POST")) {
                try {
                    SoldInsurancePolicy obj = objectMapper.readValue(input.getBody(), SoldInsurancePolicy.class);
                    soldInsurancePolicyService.saveSoldInsurancePolicy(obj);
                    Map<String, String> mapOfResponse = new HashMap<String, String>();
                    mapOfResponse.put("status", "success");
                    mapOfResponse.put("message", "Sold Insurance successfully added");
                    response.setBody(objectMapper.writeValueAsString(mapOfResponse));
                    response.setStatusCode(200);
                    return response;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    response.setBody("Error Occurred");
                    response.setStatusCode(500);
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                    response.setBody("Error Occurred");
                    response.setStatusCode(500);
                    return response;
                }
            }
        } else if (input.getResource().equals("/stats/insurance-type") && input.getHttpMethod().equals("GET")) {
            try {
                response.setBody(objectMapper.writeValueAsString(soldInsurancePolicyService.getCountByInsuranceType()));
                return response;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                response.setBody("Error Occurred");
                response.setStatusCode(500);
                return response;
            }
        } else if (input.getResource().equals("/stats/vendor") && input.getHttpMethod().equals("GET")) {
            try {
                String insuranceTypeId = input.getQueryStringParameters().get("insuranceTypeId");
                response.setBody(objectMapper.writeValueAsString(soldInsurancePolicyService.getCountByInsuranceVendor(insuranceTypeId)));
                return response;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                response.setBody("Error Occurred");
                response.setStatusCode(500);
                return response;
            }
        } else if (input.getResource().equals("/stats/agent")) {
            Map<String, String> mapOfQueryParams = input.getQueryStringParameters();
            if (input.getHttpMethod().equals("GET")) {
                if (mapOfQueryParams == null || mapOfQueryParams.size() == 0) {
                    try {
                        response.setBody(objectMapper.writeValueAsString(soldInsurancePolicyService.getCountByAgentForAll()));
                        return response;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        response.setBody("Error Occurred");
                        response.setStatusCode(500);
                        return response;
                    }
                } else if (mapOfQueryParams.size() > 0 && mapOfQueryParams.containsKey("vendorId")) {
                    try {
                        String insuranceTypeId = mapOfQueryParams.get("insuranceTypeId");
                        String vendorId = mapOfQueryParams.get("vendorId");
                        response.setBody(objectMapper.writeValueAsString(soldInsurancePolicyService.getCountByAgentForInsuranceVendor(insuranceTypeId, vendorId)));
                        return response;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        response.setBody("Error Occurred");
                        response.setStatusCode(500);
                        return response;
                    }
                } else if (mapOfQueryParams.size() > 0 && mapOfQueryParams.containsKey("insuranceTypeId")) {
                    try {
                        String insuranceTypeId = mapOfQueryParams.get("insuranceTypeId");
                        response.setBody(objectMapper.writeValueAsString(soldInsurancePolicyService.getCountByAgentForInsuranceType(insuranceTypeId)));
                        return response;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        response.setBody("Error Occurred");
                        response.setStatusCode(500);
                        return response;
                    }
                }
            }
        }
        return response;
    }

}
