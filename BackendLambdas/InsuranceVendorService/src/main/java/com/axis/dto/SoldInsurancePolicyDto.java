package com.axis.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

public class SoldInsurancePolicyDto {

    private String insuranceTypeId;
    private String insuranceTypeName;
    private String vendorId;
    private String vendorName;
    private String agentId;
    private String agentName;
    private String policyUin;
    private String policyName;
    private double insuredValue;
    private double totalAnnualPremium;
    private int totalCount;
    private double totalInsuredValue;

    public SoldInsurancePolicyDto() {
    }

    public SoldInsurancePolicyDto(String insuranceTypeId, String insuranceTypeName, String vendorId, String vendorName, String policyUin, String policyName) {
        this.insuranceTypeId = insuranceTypeId;
        this.insuranceTypeName = insuranceTypeName;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.policyUin = policyUin;
        this.policyName = policyName;
    }

    public String getInsuranceTypeId() {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(String insuranceTypeId) {
        this.insuranceTypeId = insuranceTypeId;
    }

    public String getInsuranceTypeName() {
        return insuranceTypeName;
    }

    public void setInsuranceTypeName(String insuranceTypeName) {
        this.insuranceTypeName = insuranceTypeName;
    }

    public String getVendorId() {
        return vendorId;
    }


    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPolicyUin() {
        return policyUin;
    }

    public void setPolicyUin(String policyUin) {
        this.policyUin = policyUin;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public double getInsuredValue() {
        return insuredValue;
    }

    public void setInsuredValue(double insuredValue) {
        this.insuredValue = insuredValue;
    }

    public double getTotalAnnualPremium() {
        return totalAnnualPremium;
    }

    public void setTotalAnnualPremium(double totalAnnualPremium) {
        this.totalAnnualPremium = totalAnnualPremium;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getTotalInsuredValue() {
        return totalInsuredValue;
    }

    public void setTotalInsuredValue(double totalInsuredValue) {
        this.totalInsuredValue = totalInsuredValue;
    }

    @Override
    public String toString() {
        return "SoldInsurancePolicy{" +
                "insuranceTypeId='" + insuranceTypeId + '\'' +
                ", insuranceTypeName='" + insuranceTypeName + '\'' +
                ", vendorId='" + vendorId + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", agentId='" + agentId + '\'' +
                ", policyUin='" + policyUin + '\'' +
                ", policyName='" + policyName + '\'' +
                ", insuredValue=" + insuredValue +
                ", totalAnnualPremium=" + totalAnnualPremium +
                '}';
    }
}
