package com.merchan.camunda.helloworld.zeebe;

import io.camunda.zeebe.client.ZeebeClient;

public interface ZeebeClientFactory {
    ZeebeClient create();
}
