package com.merchan.camunda.helloworld.zeebe;

import io.camunda.zeebe.client.CredentialsProvider;

import java.io.IOException;

public class LocalCredentialsProvider implements CredentialsProvider {

    @Override
    public void applyCredentials(CredentialsApplier applier) throws IOException {
        applier.put("Authorization", "Basic ZGVtbzpkZW1vCg==");
    }

    @Override
    public boolean shouldRetryRequest(StatusCode statusCode) {
        return statusCode.isUnauthorized();
    }
}
