# Zeebe Java Client Hello World

This is a simple project which uses the supported official [Camunda - Java Client](https://docs.camunda.io/docs/apis-tools/java-client/)

## Project structure and explanation
The example contains the following artifacts:
- **resources**
  - `application.properties` for configuring the Zeebe Client. You can configure it depending on your local environment setup (or SaaS):
    - `c8run`: Use this if you are using [C8 Run](https://docs.camunda.io/docs/self-managed/setup/deploy/local/c8run/) for development
    - `local-kubernetes`: Use this if you are using [Local Kubernetes Cluster](https://docs.camunda.io/docs/self-managed/setup/deploy/local/local-kubernetes-cluster/)
    - `remote`: Ue this if you are using a SaaS instance of Camunda 8 [C8 Java Client - SaaS](https://docs.camunda.io/docs/apis-tools/java-client)
  - `hello-world.bpmn`: Contains the basic Hello World process which politely will say `Hello` to whoever starts the process (sending the appropriate name) in the Log.
  - `log4j2.xml`: Using Apache LOG4J config for controlling the logging output 
- **src/main/java/com/merchan/camunda/helloworld**
  - `config/HelloWorldProperties.java`: Class which loads the `application.properties` on startup
  - `handler/SayHelloHandler.java`: Job Handler which will log `hello` when a job worker of `jobType=sayHello` is activated by Camunda
  - `zeebe`:
    - `ZeebeClientFactory.java`: Is an interface for implementing the `Factory` pattern in Java.
    - `LocalZeebeClient.java`: Is an implementation of `ZeebeClientFactory.java` which will instantiate a `ZeebeClient` instance when the property `zeebe.environment=local` (Camunda 8 Self-Managed Local without Identity and Keycloak for development).
    - `RemoteZeebeClient.java`: Is an implementation of `ZeebeClientFactory.java` which will instantiate a `ZeebeClient` instance when the property `zeebe.environment=remote` (Camunda 8 Self-Managed with Identity and Keycloak, or Camunda 8 SaaS)
    - `LocalCredentialsProvider.java`: Used by `LocalZeebeClient.java` for local development authentication (default user created in ElasticSearch is `demo/demo` when no Identity / Keycloak added into the environment).
    - `ZeebeClientFactoryProvider.java`: Class which will instantiate `LocalZeebeClient` or `RemoteZeebeClient` depending on the property  `zeebe.environment`
  - `HelloWorldApplication.java`: It is the main class which:
    - Gets a `ZeebeClient` using the above factory
    - Deploys the `hello-world.bpmn` process into Camunda
    - Attaches a Job Worker for handling jobs activated of `jobType=sayHello` and it will be executed by `SayHelloHandler.java`
    - Create a process instance of `hello-world.bpmn`
    - Keeps the Java application running just in case you want to initiate more instances from Camunda Task List

Check the console log and the process in `Camunda Desktop Modeler` for further understanding on what is happening

## Unit Testing
You can execute `HelloWorldApplicationtest.java` for testing the process.

The current setup is Java JDK 21+ importing in `pom.xml`:

```xml
<dependency>   
    <groupId>io.camunda</groupId>
    <artifactId>zeebe-process-test-extension</artifactId>
    <version>${zeebe.version}</version>
    <scope>test</scope>
</dependency>
```

Using
```java
import io.camunda.zeebe.process.test.extension.ZeebeProcessTest;
```
If you are using a lower JDK version then use the alternative you will find commented in the `java` and `pom.xml`
