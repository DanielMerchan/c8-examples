package com.merchan.camunda.helloworld.zeebe;

import io.camunda.zeebe.client.ZeebeClient;

/**
 * Interface for creating a ZeebeClient
 * @author dmerchang
 */
public interface ZeebeClientFactory {
    ZeebeClient create();
}
