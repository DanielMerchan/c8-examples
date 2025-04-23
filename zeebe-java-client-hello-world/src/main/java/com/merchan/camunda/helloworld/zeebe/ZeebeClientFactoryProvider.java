package com.merchan.camunda.helloworld.zeebe;

import com.merchan.camunda.helloworld.config.HelloWorldProperties;

public final class ZeebeClientFactoryProvider {
    public static ZeebeClientFactory getFactory() {
        String environment = HelloWorldProperties.getProperty("zeebe.environment");
        if ("local-kubernetes".equalsIgnoreCase(environment)) {
            return new LocalKubernetesZeebeClient();
        } else if ("remote".equalsIgnoreCase(environment)) {
            return new RemoteZeebeClient();
        } else if ("c8run".equalsIgnoreCase(environment)) {
            return new C8RunLocalZeebeClient();
        } else {
            throw new IllegalStateException("Unknown environment: " + environment);
        }
    }

}
