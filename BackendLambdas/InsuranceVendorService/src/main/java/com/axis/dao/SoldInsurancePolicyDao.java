package com.axis.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.axis.dto.SoldInsurancePolicyDto;
import com.axis.model.SoldInsurancePolicy;

import java.util.*;
import java.util.stream.Collectors;

public class SoldInsurancePolicyDao {

    private DynamoDBMapper dynamoDBMapper;

    public SoldInsurancePolicyDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<SoldInsurancePolicy> getListOfPolicies() {
        return this.dynamoDBMapper.scan(SoldInsurancePolicy.class, new DynamoDBScanExpression());
    }

    public List<SoldInsurancePolicy> getListOfPolicyByInsuranceTypes(String insuranceTypeId) {
        Map<String, AttributeValue> mapValue = new HashMap<String, AttributeValue>();
        mapValue.put(":itId", new AttributeValue().withS(insuranceTypeId));
        return  dynamoDBMapper.query(SoldInsurancePolicy.class, new DynamoDBQueryExpression<SoldInsurancePolicy>().withKeyConditionExpression("insuranceTypeId = :itId").withExpressionAttributeValues(mapValue));
    }

    public List<SoldInsurancePolicy> getListOfPolicyByVendor(String insuranceTypeId, String vendorId) {
        SoldInsurancePolicy soldInsurancePolicy = new SoldInsurancePolicy();
        soldInsurancePolicy.setInsuranceTypeId(insuranceTypeId);
        soldInsurancePolicy.setVendorId(vendorId);
        Condition condition = new Condition();
        condition.withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(vendorId));
        return  dynamoDBMapper.query(SoldInsurancePolicy.class, new DynamoDBQueryExpression<SoldInsurancePolicy>().withHashKeyValues(soldInsurancePolicy).withRangeKeyCondition("vendorId", condition));
    }


    public List<SoldInsurancePolicyDto> getCountByAgentForParticularPolicy(String insuranceTypeId, String vendorId, String policyUin) {
        SoldInsurancePolicy soldInsurancePolicy = new SoldInsurancePolicy();
        soldInsurancePolicy.setInsuranceTypeId(insuranceTypeId);
        soldInsurancePolicy.setVendorId(vendorId);
        Condition condition = new Condition();
        condition.withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(vendorId));

        Map<String, AttributeValue> mapOfAttributeValues = new HashMap<>();
        mapOfAttributeValues.put(":pid", new AttributeValue().withS(policyUin));

        List<SoldInsurancePolicy> listOfPolicies = dynamoDBMapper.query(SoldInsurancePolicy.class, new DynamoDBQueryExpression<SoldInsurancePolicy>().withHashKeyValues(soldInsurancePolicy).withRangeKeyCondition("vendorId", condition).withFilterExpression("policyUin = :pid").withExpressionAttributeValues(mapOfAttributeValues));
        Map<String, List<SoldInsurancePolicy>> map = listOfPolicies.stream().collect(Collectors.groupingBy(SoldInsurancePolicy::getAgentId));
        List<SoldInsurancePolicyDto> list = new ArrayList<SoldInsurancePolicyDto>();

        Set<Map.Entry<String, List<SoldInsurancePolicy>>> entriesInMap = map.entrySet();

        for (Map.Entry<String, List<SoldInsurancePolicy>> entry:
                entriesInMap) {
            List<SoldInsurancePolicy> listOfPoliciesForAgent = entry.getValue();
            SoldInsurancePolicy soldInsurancePolicyFirst = listOfPoliciesForAgent.get(0);

            double totalAnnualPremium = listOfPoliciesForAgent.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getTotalAnnualPremium));
            double totalInsuredValue = listOfPoliciesForAgent.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getInsuredValue));

            SoldInsurancePolicyDto obj1 = new SoldInsurancePolicyDto();
            obj1.setInsuranceTypeId(insuranceTypeId);
            obj1.setInsuranceTypeName(soldInsurancePolicyFirst.getInsuranceTypeName());
            obj1.setVendorId(vendorId);
            obj1.setVendorName(soldInsurancePolicyFirst.getVendorName());
            obj1.setPolicyUin(soldInsurancePolicyFirst.getPolicyUin());
            obj1.setPolicyName(soldInsurancePolicyFirst.getPolicyName());
            obj1.setAgentId(entry.getKey());
            obj1.setTotalCount(listOfPoliciesForAgent.size());
            obj1.setTotalAnnualPremium(totalAnnualPremium);
            obj1.setTotalInsuredValue(totalInsuredValue);
            list.add(obj1);
        }
        return list;
    }
    public void saveSoldInsurancePolicy(SoldInsurancePolicy obj) {
        this.dynamoDBMapper.save(obj);
    }


}
