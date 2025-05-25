package com.merchan.camunda.helloworld;

import com.merchan.camunda.helloworld.handler.SayHelloHandler;
import com.merchan.camunda.helloworld.zeebe.ZeebeClientFactoryProvider;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.BrokerInfo;
import io.camunda.zeebe.client.api.response.PartitionInfo;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.worker.JobWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello World main class
 * @author dmerchang
 */
public final class HelloWorldApplication {

    private static final Logger LOG = LogManager.getLogger(HelloWorldApplication.class);
    private static final String HELLO_WORLD_PROCESS_ID = "Process_HelloWorld";

    public static void main(final String[] args) {
        try (ZeebeClient zeebeClient = ZeebeClientFactoryProvider.getFactory().create()) {
            // Debug details about the broker to ensure we are connected
            if (LOG.isDebugEnabled()) {
                HelloWorldApplication.printBrokerInfo(zeebeClient);
            }
            // 1. Deploy hello-world.bpmn
            zeebeClient.newDeployResourceCommand()
                    .addResourceFromClasspath("hello-world.bpmn")
                    .send()
                    .join();
            // 2. Attach Job Worker for sayHello
            final JobWorker sayHelloWorker = zeebeClient.newWorker()
                    .jobType(SayHelloHandler.JOB_TYPE)
                    .handler(new SayHelloHandler())
                    .open();
            // 3. Create process instance of hello-world.bpmn
            final ProcessInstanceEvent processInstanceEvent = zeebeClient.newCreateInstanceCommand()
                    .bpmnProcessId(HelloWorldApplication.HELLO_WORLD_PROCESS_ID)
                    .latestVersion()
                    .variables(Map.of("name", "Daniel Goose"))
                    .send()
                    .join();
            LOG.info(processInstanceEvent.toString());

            // 4. Keep service alive for more job workers processing (if needed)
            Scanner sc = new Scanner(System.in);
            sc.nextInt();
            sc.close();
            sayHelloWorker.close();
        }
    }

    private static void printBrokerInfo(final ZeebeClient zeebeClient) {
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
