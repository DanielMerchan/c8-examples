package com.merchan.camunda.helloworld.zeebe;

import io.camunda.zeebe.client.CredentialsProvider;

import java.io.IOException;

/**
 * Local credentials provider for authenticating the user demo/demo
 * @author dmerchang
 */
public final class LocalCredentialsProvider implements CredentialsProvider {

    /**
     * Hardcoded Basic authentication using demo/demo
     * @param applier - Includes metadata and headers used for authentication
     * @throws IOException
     */
    @Override
    public void applyCredentials(CredentialsApplier applier) throws IOException {
        applier.put("Authorization", "Basic ZGVtbzpkZW1vCg==");
    }

    /**
     * Determines if the request should be retried
     * @param statusCode - Result of the failed call
     * @return true if the request should be retried
     */
    @Override
    public boolean shouldRetryRequest(StatusCode statusCode) {
        return statusCode.isUnauthorized();
    }
}
