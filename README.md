# Camunda 8 Examples
This repository contains multiple projects for learning or solving different use cases you may encounter with Camunda 8

## How to test the projects locally
For the shake of simplicity, the majority of the projects are developed against a local development environment of Camunda 8 

You can easily se tup this by following any of the following options:
- [**Docker Compose**](https://docs.camunda.io/docs/self-managed/setup/deploy/local/docker-compose/): This will easily up the Camunda 8 services you need for development
- [**Local Kubernetes Cluster**](https://docs.camunda.io/docs/self-managed/setup/deploy/local/local-kubernetes-cluster/): It is a setup more close to a real Camunda 8 Self-Managed / SaaS environment

Use the one you prefer, in my case I use **Local Kubernetes Cluster**
**IMPORTANT:** By default, the local development Zeebe does not enforce security and you can invoke it without sending any credentials.

## How to test the projects in Camunda SaaS or Self-Managed with Identity 
SaaS and proper Self-Managed set up will require that your **Zeebe Client** provides some security context to authenticate and get authorize.

The projects contains a section that you can uncomment and then set your Zeebe credentials in that case.