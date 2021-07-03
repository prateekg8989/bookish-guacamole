package com.axis.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.AttributeEncryptor;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.DynamoDBEncryptor;
import com.amazonaws.services.dynamodbv2.datamodeling.encryption.providers.DirectKmsMaterialProvider;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;

public class DynamodbConfig {


    public static DynamoDBMapper dynamoDBMapper() {
        String cmkArn = "arn:aws:kms:us-east-2:423814357905:key/16ef0224-e9be-4375-aa40-ac31e47b655c";
        AWSKMS kmsClient = AWSKMSClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
        final DirectKmsMaterialProvider cmp = new DirectKmsMaterialProvider(kmsClient, cmkArn);
        final DynamoDBEncryptor encryptor = DynamoDBEncryptor.getInstance(cmp);
        DynamoDBMapperConfig mapperConfig =
                DynamoDBMapperConfig.builder().withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.PUT).build();
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).build();
        return new DynamoDBMapper(amazonDynamoDB, mapperConfig, new AttributeEncryptor(encryptor));
    }

}
