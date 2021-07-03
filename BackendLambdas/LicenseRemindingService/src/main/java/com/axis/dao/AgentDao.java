package com.axis.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.axis.config.DynamodbConfig;
import com.axis.model.Agent;

import java.text.SimpleDateFormat;
import java.util.*;

public class AgentDao {
    private DynamoDBMapper dynamoDBMapper = DynamodbConfig.dynamoDBMapper();

    public List<Agent> getAgentWhoseLicenseExpiredOrGoingToExpire() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 14);
        Date newDate = cal.getTime();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String _14DaysAfterDate = dateFormatter.format(newDate);
        System.out.println(newDate.toString());
        Map<String, AttributeValue> mapOfAttributeValues = new HashMap<>();
        mapOfAttributeValues.put(":_14DaysAfterDate", new AttributeValue().withS(_14DaysAfterDate));
        return dynamoDBMapper.scan(Agent.class, new DynamoDBScanExpression().withFilterExpression("licenseExpiryDate < :_14DaysAfterDate").withExpressionAttributeValues(mapOfAttributeValues));
    }


}
