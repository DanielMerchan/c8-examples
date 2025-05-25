package com.merchan.camunda.helloworld;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.BrokerInfo;
import io.camunda.zeebe.client.api.response.PartitionInfo;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@Deployment(resources = "classpath:hello-world.bpmn")
public class HelloWorldSpringApplication implements CommandLineRunner {

    private final ZeebeClient zeebeClient;

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldSpringApplication.class);

    public HelloWorldSpringApplication(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var processDefinitionKey = "Process_HelloWorld";
        var event = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(processDefinitionKey)
                .latestVersion()
                .variables(Map.of("name", "Daniel Goose"))
                .send()
                .join();
        LOG.info("started a process: {}", event.getProcessInstanceKey());
    }

    private static void printBrokerInfo(final ZeebeClient zeebeClient) {

        if (LOG.isDebugEnabled()) {
            HelloWorldSpringApplication.printBrokerInfo(zeebeClient);
        }

        List<BrokerInfo> brokers = zeebeClient
                .newTopologyRequest()
                .send()
                .join()
                .getBrokers();

        for (var broker : brokers) {
            LOG.debug(broker.toString());
            List<PartitionInfo> partitions = broker.getPartitions();
            for (var partition : partitions) {
                LOG.debug("partition: {}", partition.toString());
            }
        }
    }
}
