package com.merchan.camunda.helloworld.zeebe;

import com.merchan.camunda.helloworld.config.HelloWorldProperties;

public final class ZeebeClientFactoryProvider {
    public static ZeebeClientFactory getFactory() {
        String environment = HelloWorldProperties.getProperty("zeebe.environment");

        if ("local".equalsIgnoreCase(environment)) {
            return new LocalZeebeClient();
        } else if ("remote".equalsIgnoreCase(environment)) {
            return new RemoteZeebeClient();
        } else {
            throw new IllegalStateException("Unknown environment: " + environment);
        }
    }

}
