package com.merchan.camunda.helloworld;

// When using the embedded Zeebe engine (Java 21+)
import com.merchan.camunda.helloworld.handler.SayHelloHandler;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivateJobsResponse;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.extension.ZeebeProcessTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.camunda.zeebe.process.test.assertions.BpmnAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.util.Map;

// When using testcontainers (Java 8+)
//import io.camunda.zeebe.process.test.extension.testcontainer.ZeebeProcessTest;

/**
 * Unit tests over HelloWorldApplication and hello-world.bpmn process
 * @author dmerchang
 */
@ZeebeProcessTest
public final class HelloWorldApplicationsTest {

    private static final String HELLO_WORLD_PROCESS_ID = "Process_HelloWorld";

    private ZeebeTestEngine engine;
    private ZeebeClient client;

    @BeforeEach
    public void setup() {
        DeploymentEvent deploymentEvent = client.newDeployResourceCommand()
                .addResourceFromClasspath("hello-world.bpmn")
                .send()
                .join();
        assertThat(deploymentEvent).containsProcessesByBpmnProcessId(HELLO_WORLD_PROCESS_ID);
    }

    @Test
    public void testHappyPath() throws Exception {
        // given
        SayHelloHandler sayHelloHandler = new SayHelloHandler();

        // when
        ProcessInstanceEvent processInstanceEvent = startInstance(HELLO_WORLD_PROCESS_ID, Map.of("name", "Daniel"));
        completeJob("sayHello", 1, sayHelloHandler);

        // then
        assertThat(processInstanceEvent)
                .hasPassedElement("Event_GreetingsCompleted")
                .hasVariableWithValue("name", "Daniel")
                .isCompleted();
    }

    public void completeJob(String type, int count, JobHandler handler) throws Exception {
        ActivateJobsResponse activateJobsResponse = client.newActivateJobsCommand()
                .jobType(type)
                .maxJobsToActivate(count)
                .send().join();
        List<ActivatedJob> activatedJobs = activateJobsResponse.getJobs();
        if(activatedJobs.size() != count){
            fail("No job activated for type " + type);
        }

        for (ActivatedJob job:activatedJobs) {
            handler.handle(client, job);
        }

        engine.waitForIdleState(Duration.ofSeconds(1));
    }

    public ProcessInstanceEvent startInstance(String id, Map<String, Object> variables){
        ProcessInstanceEvent processInstance = client.newCreateInstanceCommand()
                .bpmnProcessId(id)
                .latestVersion()
                .variables(variables)
                .send().join();
        assertThat(processInstance).isStarted();
        return processInstance;
    }

}
