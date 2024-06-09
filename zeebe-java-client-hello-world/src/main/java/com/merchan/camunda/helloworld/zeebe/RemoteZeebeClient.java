package com.merchan.camunda.helloworld.zeebe;

import com.merchan.camunda.helloworld.config.HelloWorldProperties;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import java.net.URI;

public class RemoteZeebeClient implements ZeebeClientFactory {

    private static final String PROPERTIES_PATH = "src/main/resources/application.properties";

    private static final String ZEEBE_GRPC = HelloWorldProperties.getProperty("zeebe.grpc");
    private static final String ZEEBE_REST = HelloWorldProperties.getProperty("zeebe.rest");
    private static final String AUDIENCE = HelloWorldProperties.getProperty("audience");
    private static final String CLIENT_ID = HelloWorldProperties.getProperty("clientId");
    private static final String CLIENT_SECRET = HelloWorldProperties.getProperty("clientSecret");
    private static final String OAUTH2 = HelloWorldProperties.getProperty("zeebe.client.OAuth");

    public RemoteZeebeClient() {
    }

    @Override
    public ZeebeClient create() {
        OAuthCredentialsProvider credentialsProviderBuilder = new OAuthCredentialsProviderBuilder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .audience(AUDIENCE)
                .authorizationServerUrl(OAUTH2).build();
        try (ZeebeClient client = ZeebeClient.newClientBuilder()
                .grpcAddress(URI.create(ZEEBE_GRPC))
                .restAddress(URI.create(ZEEBE_REST))
                .credentialsProvider(credentialsProviderBuilder)
                .build()) {
            client.newTopologyRequest().send().join();
            return client;
        }
    }
}
