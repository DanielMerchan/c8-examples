package com.merchan.camunda.helloworld;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.assertions.BpmnAssert;
import io.camunda.zeebe.process.test.assertions.DeploymentAssert;
import io.camunda.zeebe.spring.test.ZeebeSpringTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ZeebeSpringTest
public class HelloWorldSpringApplicationTest {

    @Autowired
    private ZeebeClient zeebeClient;

    @Autowired
    private ZeebeTestEngine zeebeTestEngine;

    private static final String HELLO_WORLD_PROCESS_ID = "Process_HelloWorld";


    @BeforeEach
    public void setup() {
        DeploymentEvent event = zeebeClient.newDeployResourceCommand()
                .addResourceFromClasspath("hello-world.bpmn")
                .send()
                .join();
        DeploymentAssert assertions = BpmnAssert.assertThat(event);
        assertions.containsProcessesByBpmnProcessId(HELLO_WORLD_PROCESS_ID);
    }

    @Test
    public void testHappyPath() {

    }
}
