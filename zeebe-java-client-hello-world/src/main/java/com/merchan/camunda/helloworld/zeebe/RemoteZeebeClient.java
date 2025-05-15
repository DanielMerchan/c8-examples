package com.merchan.camunda.helloworld.zeebe;

import com.merchan.camunda.helloworld.config.HelloWorldProperties;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import java.net.URI;

/**
 * Remote client used to connect to Camunda 8 SaaS or a proper Camunda 8 Self-Managed installation with Identity and Keycloak
 * @author dmerchang
 */
public final class RemoteZeebeClient implements ZeebeClientFactory {

    /**
     * Attributes to obtain the necessary values for establishing the Zeebe Client
     */
    private static final String ZEEBE_GRPC = HelloWorldProperties.getProperty("zeebe.client.broker.grpcAddress");
    private static final String ZEEBE_REST = HelloWorldProperties.getProperty("zeebe.client.broker.restAddress");
    private static final String AUDIENCE = HelloWorldProperties.getProperty("zeebe.client.audience");
    private static final String CLIENT_ID = HelloWorldProperties.getProperty("zeebe.client.clientId");
    private static final String CLIENT_SECRET = HelloWorldProperties.getProperty("zeebe.client.clientSecret");
    private static final String OAUTH2 = HelloWorldProperties.getProperty("zeebe.client.OAuth");

    /**
     * Default constructor
     */
    public RemoteZeebeClient() {
    }

    /**
     * Initializes a ZeebeClient based on the Remote settings
     * @return ZeebeClient
     */
    @Override
    public ZeebeClient create() {
        OAuthCredentialsProvider credentialsProviderBuilder = new OAuthCredentialsProviderBuilder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .audience(AUDIENCE)
                .authorizationServerUrl(OAUTH2).build();
        return ZeebeClient.newClientBuilder()
                .grpcAddress(URI.create(ZEEBE_GRPC))
                .restAddress(URI.create(ZEEBE_REST))
                .credentialsProvider(credentialsProviderBuilder)
                .build();
    }
}
