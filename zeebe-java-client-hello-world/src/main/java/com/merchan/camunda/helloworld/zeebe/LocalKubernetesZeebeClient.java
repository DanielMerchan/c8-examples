package com.merchan.camunda.helloworld.zeebe;

import com.merchan.camunda.helloworld.config.HelloWorldProperties;
import io.camunda.zeebe.client.ZeebeClient;

import java.net.URI;

/**
 * Local ZeebeClient used for local development environment using Local Kubernetes Cluster or Docker Compose
 * @author dmerchang
 */
public final class LocalKubernetesZeebeClient implements ZeebeClientFactory {

    private static final String ZEEBE_GRPC = HelloWorldProperties.getProperty("zeebe.client.broker.grpcAddress");
    private static final String ZEEBE_REST = HelloWorldProperties.getProperty("zeebe.client.broker.restAddress");

    public LocalKubernetesZeebeClient() {
    }

    @Override
    public ZeebeClient create() {
        return ZeebeClient.newClientBuilder()
                .grpcAddress(URI.create(ZEEBE_GRPC))
                .restAddress(URI.create(ZEEBE_REST))
                .usePlaintext()
                .credentialsProvider(new LocalKubernetesCredentialsProvider())
                .build();
    }
}
