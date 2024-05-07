# The basics of container orchestration

The use of docker compose and docker swarm tools to run containers together and orchestrate them in a simple way.

## Contents

1. [Chapter I](#chapter-i)
2. [Chapter II](#chapter-ii) \
   2.1. [Running multiple docker containers using docker compose](#part-1-running-multiple-docker-containers-using-docker-compose-docker-compose) \
   2.2. [Creating virtual machines](#part-2-creating-virtual-machines) \
   2.3. [Creating a simple docker swarm](#part-3-creating-a-simple-docker-swarm)

## Chapter I

As you already know, a **docker** is a platform for building, running and *delivering* applications, designed to run software almost regardless of the machine on which it will physically run. This effect is achieved by *containerizing* the software, that is placing the executable software in a separate sub-environment - a *container* containing all the necessary dependencies and, if the container image is well composed, nothing else. The problem of dependencies is actually much more serious than it might seem at first glance. The flaws here are not only missing files or libraries, but possibly versions of libraries or even a language, values of environment variables and many other aspects that may, if not break the software completely, but significantly affect its performance. The container guarantees *the same* execution of the containerized software on any machine, because it contains everything you need for the software inside to run consistently. This aspect is especially important when developing web services where frequent software updates and re-deployment on different machines is possible.

Usually, a serious application does not consist of a single program, i.e. it does not have a monolithic structure, but a microservice one. Although, formally, each service can be deployed in the same container, this approach does not conform to SOLID principles and clean architecture in general. For example, this approach makes it practically impossible or significantly more difficult to deploy a new version of a separate microservice. You need to remember the basic rule of containerization: "1 microservice - 1 container". 

Now that the rule about distributing microservices to containers has been defined, it is worth recalling another tool - **docker compose**, which allows you to run several containers together. **Docker compose** not only allows you to run multiple docker containers simultaneously, but also provides the ability to define their interaction, which is a necessary condition for deploying a microservice application.

However, **docker compose** alone allows you to run containers on only one machine. In reality, microservices applications are distributed on different machines: real or virtual, it does not matter. Usually, of course, these turn out to be virtual machines, but not necessarily all the virtual machines used by the same software are on the same real machine. Often this is not even the case. This is where **docker swarm** comes in. In general, the phrase **docker swarm** means both a group of machines combined into one *cluster*, with docker containers running on them linked together, and a tool which combines the machines into such a *cluster*. A *cluster* is a combination of machines or *nodes* into a single network with the load distributed over these nodes in the form of applications executed in containers. Special programs called orchestrators are responsible for running and curating such a cluster. **Docker swarm** is just one of them. **Docker swarm** is a relatively simple and easy to learn orchestrator with all the basic tools.

Finally, where do we get the machines that will be used as the basis for cluster nodes? The answer: virtualization. One of the most popular and simple tools for creating virtual machines is **vagrant**. **Vagrant** allows you to quickly, with just a couple of commands, create multiple small virtual machines.

## Chapter II

The result of the work must be a report with detailed descriptions of the implementation of each of the points with screenshots. The report is prepared as a markdown file in the `src` directory named `REPORT.MD`.

## Part 1. Running multiple docker containers using docker compose

It is worth recalling how docker-compose works! First, let's try to run the microservice application from the `src` folder so that the postman tests are successful.

**== Task ==**

1) Write a Dockerfile for each individual microservice. The necessary dependencies are described in the materials. Write the size of the built images of any service in the report in different ways.

2) Write a docker-compose file that performs a correct interaction of services. Forward ports to access the gateway service and session service from the local machine. Help on docker compose is in the materials.

3) Build and deploy a web service using a docker compose file written on the local machine.

4) Run the tests you have prepared through postman and make sure they are all successful. You can find instructions on how to run the tests in the materials. Write the test results in the report.

## Part 2. Creating virtual machines

It's time to prepare the base for future cluster nodes. Let's create a virtual machine.

**== Task ==**

1) Install and initialize Vagrant at the root of the project. Write a Vagrantfile for one virtual machine. Move the source code of the web service to the virtual machine's working directory. Help on vagrant is in the materials.

2) Use the console to go inside the virtual machine and make sure that the source code is in place. Stop and destroy the virtual machine.

## Part 3. Creating a simple docker swarm

Now it's time to create your first docker swarm!

**== Task ==**

1) Modify Vagrantfile to create three machines: manager01, worker01, worker02. Write shell scripts to install docker inside machines, initialize and connect to docker swarm. Help on docker swarm is in the materials.

2) Load the built images on the docker hub and modify the docker-compose file to load the images located on the docker hub.

3) Run virtual machines and move the docker-compose file to the manager. Run the service stack using the docker-compose file you wrote.

4) Configure an nginx-based proxy to access the gateway service and session service via the overlay network. Make the gateway service and session service themselves unavailable directly.

5) Run the prepared tests through postman and make sure that they are all successful. Write the test results in the report.

6) Using docker commands, show the distribution of containers by nodes in the report.

7) Install a separate Portainer stack inside the cluster. Show a visualization of the distribution of tasks over the nodes using the Portainer in the report.

💡 [Click here](https://forms.yandex.ru/cloud/6475b911c09c0225003a0fd6/), **to share feedback on this project with us**. It's anonymous and will help the Product team make your learning better.
