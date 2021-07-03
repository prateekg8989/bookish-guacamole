package com.axis.config;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.SesClientBuilder;

public class SesConfig {

    public static SesClient getSesClient() {
        return SesClient.builder().credentialsProvider(EnvironmentVariableCredentialsProvider.create()).region(Region.US_EAST_2).httpClientBuilder(UrlConnectionHttpClient.builder()).build();
    }
}
