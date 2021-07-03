package com.axis.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.DoNotEncrypt;

@DynamoDBTable(tableName = "User")
public class User {
    @DynamoDBHashKey(attributeName = "user-id")
    private String userId;

    @DynamoDBAttribute
    @DoNotEncrypt
    private String email;

    @DynamoDBAttribute
    @DynamoDBAutoGeneratedDefault("agent")
    @DoNotEncrypt
    private String role;

    @DynamoDBAttribute(attributeName = "isDisable")
    @DoNotEncrypt
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    private boolean isDisable;

    public User() {
    }

    public User(String userId, String email, String role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(boolean disable) {
        isDisable = disable;
    }
}
