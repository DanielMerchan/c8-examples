package com.merchan.camunda.helloworld;

import com.merchan.camunda.helloworld.zeebe.ZeebeClientFactoryProvider;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.BrokerInfo;
import io.camunda.zeebe.client.api.response.PartitionInfo;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Main class
 * @author dmerchang
 */
public final class HelloWorldApplication {

    public HelloWorldApplication() {
    }

    public static void main(final String[] args) {
        try (ZeebeClient zeebeClient = ZeebeClientFactoryProvider.getFactory().create()) {
            System.out.println("Hello World!");
            List<BrokerInfo> brokers = zeebeClient
                    .newTopologyRequest()
                    .send()
                    .join()
                    .getBrokers();

            for (var broker : brokers) {
                System.out.println(broker.getAddress());
                List<PartitionInfo> partitions = broker.getPartitions();
                for (var partition : partitions) {
                    System.out.println(partition.getPartitionId());
                }
            }
        }
    }
}
