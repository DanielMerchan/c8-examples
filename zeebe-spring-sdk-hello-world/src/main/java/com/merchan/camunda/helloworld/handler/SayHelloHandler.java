package com.merchan.camunda.helloworld.handler;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SayHelloHandler {

    private final static Logger LOG = LoggerFactory.getLogger(SayHelloHandler.class);

    @JobWorker(type = "sayHello")
    public void handle(@Variable(name = "name") String name) {
        LOG.info("Saying hello: {}", name);
    }
}

