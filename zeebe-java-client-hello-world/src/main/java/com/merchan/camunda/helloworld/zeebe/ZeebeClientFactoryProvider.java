package com.merchan.camunda.helloworld.zeebe;

import com.merchan.camunda.helloworld.config.HelloWorldProperties;

/**
 * Factory Provider for returning the appropriate Zeebe Client depending on where the code is being executed
 * @author dmerchang
 */
public final class ZeebeClientFactoryProvider {

    private static final String LOCAL_KUBERNETES_CONFIG = "local-kubernetes";
    private static final String REMOTE_CONFIG = "remote";
    private static final String C8RUN_CONFIG = "c8run";

    /**
     * Returns the appropriate Zeebe Client depending on the application.properties configuration
     * @return ZeebeClientFactory
     */
    public static ZeebeClientFactory getFactory() {
        String environment = HelloWorldProperties.getProperty("zeebe.environment");
        if (LOCAL_KUBERNETES_CONFIG.equalsIgnoreCase(environment)) {
            return new LocalKubernetesZeebeClient();
        } else if (REMOTE_CONFIG.equalsIgnoreCase(environment)) {
            return new RemoteZeebeClient();
        } else if (C8RUN_CONFIG.equalsIgnoreCase(environment)) {
            return new C8RunLocalZeebeClient();
        } else {
            throw new IllegalStateException("Unknown environment: " + environment);
        }
    }

}
