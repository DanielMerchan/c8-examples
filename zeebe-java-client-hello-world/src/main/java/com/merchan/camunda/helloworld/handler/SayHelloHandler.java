package com.merchan.camunda.helloworld.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Say hello - job handler which will be associated to the "sayHello" job type
 * @author dmerchang
 */
public final class SayHelloHandler implements JobHandler {

    private static final Logger LOG = LogManager.getLogger(SayHelloHandler.class);

    public static final String JOB_TYPE = "sayHello";

    /**
     * Handles the job sayHello
     * @param client - JobClient for sending a command to Zeebe about the job result
     * @param job - Contains the activated job information along with the variables
     */
    @Override
    public void handle(JobClient client, ActivatedJob job){
        final String name = (String) job.getVariablesAsMap().get("name");
        LOG.info("Saying hello '{}'", name);
        client.newCompleteCommand(job.getKey()).send()
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        LOG.error("Failed to complete job {} with exception", job.getKey(), exception);
                    }
                });
    }
}
