package com.axis.service;


import com.axis.config.DynamodbConfig;
import com.axis.dao.SoldInsurancePolicyDao;
import com.axis.dto.SoldInsurancePolicyDto;
import com.axis.model.SoldInsurancePolicy;

import java.util.*;
import java.util.stream.Collectors;

public class SoldInsurancePolicyService {

    private SoldInsurancePolicyDao soldInsurancePolicyDao = new SoldInsurancePolicyDao(DynamodbConfig.dynamoDBMapper());

    public List<SoldInsurancePolicy> getListOfPolicies() {
        return this.soldInsurancePolicyDao.getListOfPolicies();
    }

    public List<SoldInsurancePolicy> getListOfPolicyByInsuranceTypes(String insuranceTypeId) {
        return this.soldInsurancePolicyDao.getListOfPolicyByInsuranceTypes(insuranceTypeId);
    }

    public List<SoldInsurancePolicy> getListOfPolicyByVendor(String insuranceTypeId, String vendorId) {
        return this.soldInsurancePolicyDao.getListOfPolicyByVendor(insuranceTypeId, vendorId);
    }


    public List<SoldInsurancePolicyDto> getCountByInsuranceType() {
        List<SoldInsurancePolicy> listOfPolicies = this.soldInsurancePolicyDao.getListOfPolicies();
        Map<String, List<SoldInsurancePolicy>> map = listOfPolicies.stream().collect(Collectors.groupingBy(SoldInsurancePolicy::getInsuranceTypeId));
        List<SoldInsurancePolicyDto> list = new ArrayList<SoldInsurancePolicyDto>();
        Set<Map.Entry<String, List<SoldInsurancePolicy>>> entries = map.entrySet();
        for (Map.Entry<String, List<SoldInsurancePolicy>> entry :
                entries) {
            List<SoldInsurancePolicy> listOfPoliciesForInsuranceType = entry.getValue();
            SoldInsurancePolicy soldInsurancePolicyFirst = listOfPoliciesForInsuranceType.get(0);
            double totalAnnualPremium = listOfPoliciesForInsuranceType.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getTotalAnnualPremium));
            double totalInsuredValue = listOfPoliciesForInsuranceType.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getInsuredValue));
            SoldInsurancePolicyDto obj1 = new SoldInsurancePolicyDto();
            obj1.setInsuranceTypeId(entry.getKey());
            obj1.setInsuranceTypeName(soldInsurancePolicyFirst.getInsuranceTypeName());
            obj1.setTotalCount(listOfPoliciesForInsuranceType.size());
            obj1.setTotalAnnualPremium(totalAnnualPremium);
            obj1.setTotalInsuredValue(totalInsuredValue);
            list.add(obj1);
        }
        return list;
    }

    public List<SoldInsurancePolicyDto> getCountByInsuranceVendor(String insuranceTypeId) {
        List<SoldInsurancePolicy> listOfPolicies = this.soldInsurancePolicyDao.getListOfPolicyByInsuranceTypes(insuranceTypeId);
        Map<String, List<SoldInsurancePolicy>> map = listOfPolicies.stream().collect(Collectors.groupingBy(SoldInsurancePolicy::getVendorId));
        List<SoldInsurancePolicyDto> list = new ArrayList<SoldInsurancePolicyDto>();
        Set<Map.Entry<String, List<SoldInsurancePolicy>>> entries = map.entrySet();
        for (Map.Entry<String, List<SoldInsurancePolicy>> entry :
                entries) {
            List<SoldInsurancePolicy> listOfPoliciesForVendor = entry.getValue();
            SoldInsurancePolicy soldInsurancePolicyFirst = listOfPoliciesForVendor.get(0);

            double totalAnnualPremium = listOfPoliciesForVendor.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getTotalAnnualPremium));
            double totalInsuredValue = listOfPoliciesForVendor.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getInsuredValue));

            SoldInsurancePolicyDto obj1 = new SoldInsurancePolicyDto();
            obj1.setInsuranceTypeId(insuranceTypeId);
            obj1.setInsuranceTypeName(soldInsurancePolicyFirst.getInsuranceTypeName());
            obj1.setVendorId(entry.getKey());
            obj1.setVendorName(soldInsurancePolicyFirst.getVendorName());
            obj1.setTotalCount(listOfPoliciesForVendor.size());
            obj1.setTotalAnnualPremium(totalAnnualPremium);
            obj1.setTotalInsuredValue(totalInsuredValue);
            list.add(obj1);
        }
        return list;
    }


    public List<SoldInsurancePolicyDto> getCountByAgentForInsuranceType(String insuranceTypeId) {
        List<SoldInsurancePolicy> listOfPolicies = this.soldInsurancePolicyDao.getListOfPolicyByInsuranceTypes(insuranceTypeId);
        Map<String, List<SoldInsurancePolicy>> map = listOfPolicies.stream().collect(Collectors.groupingBy(SoldInsurancePolicy::getAgentId));
        List<SoldInsurancePolicyDto> list = new ArrayList<SoldInsurancePolicyDto>();
        Set<Map.Entry<String, List<SoldInsurancePolicy>>> entries = map.entrySet();
        for (Map.Entry<String, List<SoldInsurancePolicy>> entry :
                entries) {
            List<SoldInsurancePolicy> listOfPoliciesForAgent = entry.getValue();
            SoldInsurancePolicy soldInsurancePolicyFirst = listOfPoliciesForAgent.get(0);

            double totalAnnualPremium = listOfPoliciesForAgent.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getTotalAnnualPremium));
            double totalInsuredValue = listOfPoliciesForAgent.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getInsuredValue));

            SoldInsurancePolicyDto obj1 = new SoldInsurancePolicyDto();
            obj1.setInsuranceTypeId(insuranceTypeId);
            obj1.setInsuranceTypeName(soldInsurancePolicyFirst.getInsuranceTypeName());
            obj1.setAgentId(entry.getKey());
            obj1.setAgentName(listOfPoliciesForAgent.get(0).getAgentName());
            obj1.setTotalCount(listOfPoliciesForAgent.size());
            obj1.setTotalAnnualPremium(totalAnnualPremium);
            obj1.setTotalInsuredValue(totalInsuredValue);
            list.add(obj1);
        }
        return list;
    }

    public List<SoldInsurancePolicyDto> getCountByAgentForInsuranceVendor(String insuranceTypeId, String vendorId) {
        List<SoldInsurancePolicy> listOfPolicies = this.soldInsurancePolicyDao.getListOfPolicyByVendor(insuranceTypeId, vendorId);
        Map<String, List<SoldInsurancePolicy>> map = listOfPolicies.stream().collect(Collectors.groupingBy(SoldInsurancePolicy::getAgentId));
        List<SoldInsurancePolicyDto> list = new ArrayList<SoldInsurancePolicyDto>();
        Set<Map.Entry<String, List<SoldInsurancePolicy>>> entries = map.entrySet();
        for (Map.Entry<String, List<SoldInsurancePolicy>> entry :
                entries) {
            List<SoldInsurancePolicy> listOfPoliciesForAgent = entry.getValue();
            SoldInsurancePolicy soldInsurancePolicyFirst = listOfPoliciesForAgent.get(0);

            double totalAnnualPremium = listOfPoliciesForAgent.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getTotalAnnualPremium));
            double totalInsuredValue = listOfPoliciesForAgent.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getInsuredValue));

            SoldInsurancePolicyDto obj1 = new SoldInsurancePolicyDto();
            obj1.setInsuranceTypeId(insuranceTypeId);
            obj1.setInsuranceTypeName(soldInsurancePolicyFirst.getInsuranceTypeName());
            obj1.setVendorId(vendorId);
            obj1.setVendorName(soldInsurancePolicyFirst.getVendorName());
            obj1.setAgentId(entry.getKey());
            obj1.setAgentName(listOfPoliciesForAgent.get(0).getAgentName());
            obj1.setTotalCount(listOfPoliciesForAgent.size());
            obj1.setTotalAnnualPremium(totalAnnualPremium);
            obj1.setTotalInsuredValue(totalInsuredValue);
            list.add(obj1);
        }
        return list;
    }


    public List<SoldInsurancePolicyDto> getCountByAgentForAll() {

        List<SoldInsurancePolicy> listOfPolicies = this.soldInsurancePolicyDao.getListOfPolicies();
        Map<String, List<SoldInsurancePolicy>> map = listOfPolicies.stream().collect(Collectors.groupingBy(SoldInsurancePolicy::getAgentId));
        List<SoldInsurancePolicyDto> list = new ArrayList<SoldInsurancePolicyDto>();
        Set<Map.Entry<String, List<SoldInsurancePolicy>>> entries = map.entrySet();
        for (Map.Entry<String, List<SoldInsurancePolicy>> entry :
                entries) {
            List<SoldInsurancePolicy> listOfPoliciesForAgent = entry.getValue();

            double totalAnnualPremium = listOfPoliciesForAgent.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getTotalAnnualPremium));
            double totalInsuredValue = listOfPoliciesForAgent.stream().collect(Collectors.summingDouble(SoldInsurancePolicy::getInsuredValue));

            SoldInsurancePolicyDto obj1 = new SoldInsurancePolicyDto();
            obj1.setAgentId(entry.getKey());
            obj1.setAgentName(listOfPoliciesForAgent.get(0).getAgentName());
            obj1.setTotalCount(listOfPoliciesForAgent.size());
            obj1.setTotalAnnualPremium(totalAnnualPremium);
            obj1.setTotalInsuredValue(totalInsuredValue);
            list.add(obj1);
        }
        return list;
    }

    public void saveSoldInsurancePolicy(SoldInsurancePolicy obj) {
        this.soldInsurancePolicyDao.saveSoldInsurancePolicy(obj);
    }


}
