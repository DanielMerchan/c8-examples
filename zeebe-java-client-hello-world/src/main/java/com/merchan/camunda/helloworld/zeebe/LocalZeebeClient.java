package com.merchan.camunda.helloworld.zeebe;

import io.camunda.zeebe.client.CredentialsProvider;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.NoopCredentialsProvider;

import java.net.URI;

public class LocalZeebeClient implements ZeebeClientFactory {

    @Override
    public ZeebeClient create() {
        return ZeebeClient.newClientBuilder()
                .grpcAddress(URI.create("http://zeebe.camunda.local:26500"))
                .restAddress(URI.create("http://camunda.local"))
                .credentialsProvider(new LocalCredentialsProvider())
                .build();
    }
}
