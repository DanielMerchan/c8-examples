# Camunda 8 Examples
This repository contains multiple projects for learning or solving different use cases you may encounter with Camunda 8

## Camunda 8 SaaS or Local Environment
If you have a SaaS (non-trial), SaaS (trial with expiration) you will be able to execute every project by just updating the Zeebe client configuration in `application.properties` or `application.yaml`

If you do not have any of these environments you can start a local one by following:
- [**Docker Compose**](https://docs.camunda.io/docs/self-managed/setup/deploy/local/docker-compose/): This will easily up the Camunda 8 services you need for development
- [**Local Kubernetes Cluster**](https://docs.camunda.io/docs/self-managed/setup/deploy/local/local-kubernetes-cluster/): It is a setup more close to a real Camunda 8 Self-Managed / SaaS environment

If you decide to go over **Local Kubernetes Cluster** with **Ingress Nginx Controller** the setup requires extra steps:
- Configure a TLS/SSL connection to the Nginx Controller using certificates (properly specified in Camunda 8 blog below)
- Import those certificates into your Java JDK in use (specified in my blog a the bottom)

You can follow for the above setup:
- [Official Blog Camunda Self-Managed for Absolute Beginners](https://camunda.com/blog/2023/10/camunda-self-managed-for-absolute-beginners/)
- [Camunda 8 Self-Managed Local Environment](https://magicpigeoncodecove.blogspot.com/2024/06/camunda-8-local-environment.html)

## How to test the projects locally

Most of the projects will deploy and execute instances of the processes to test. 

However, in some I will add some JUnit tests.
