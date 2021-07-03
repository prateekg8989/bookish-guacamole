package com.axis.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.DoNotEncrypt;

import java.util.Date;

@DynamoDBTable(tableName = "Agent")
public class Agent {
    @DoNotEncrypt
    @DynamoDBAttribute(attributeName = "user-id")
    private String userId;
    @DynamoDBHashKey
    private String email;
    @DoNotEncrypt
    @DynamoDBAttribute
    private String name;
    @DynamoDBAttribute
    private String address;
    @DynamoDBAttribute
    private Date dob;
    @DynamoDBAttribute
    private String crmId;
    @DynamoDBAttribute
    private String teleCallingId;

    @DynamoDBAttribute
    private String licenseUrnNo;
    @DynamoDBAttribute
    private Date licenseIssueDate;
    @DynamoDBAttribute
    private Date licenseExpiryDate;
    @DoNotEncrypt
    @DynamoDBAttribute(attributeName = "isDisable")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    private boolean isDisable;

    public Agent() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public String getTeleCallingId() {
        return teleCallingId;
    }

    public void setTeleCallingId(String teleCallingId) {
        this.teleCallingId = teleCallingId;
    }

    public String getLicenseUrnNo() {
        return licenseUrnNo;
    }

    public void setLicenseUrnNo(String licenseUrnNo) {
        this.licenseUrnNo = licenseUrnNo;
    }

    public Date getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(Date licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }

    public Date getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(Date licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public boolean getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(boolean disable) {
        isDisable = disable;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dob=" + dob +
                ", crmId='" + crmId + '\'' +
                ", teleCallingId='" + teleCallingId + '\'' +
                ", licenseUrnNo='" + licenseUrnNo + '\'' +
                ", licenseIssueDate=" + licenseIssueDate +
                ", licenseExpiryDate=" + licenseExpiryDate +
                '}';
    }
}
