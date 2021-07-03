package com.axis.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.axis.model.User;

public class UserDao {

    private DynamoDBMapper dynamoDBMapper;

    public UserDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void saveUser(User user) {
        this.dynamoDBMapper.save(user);
    }
}
