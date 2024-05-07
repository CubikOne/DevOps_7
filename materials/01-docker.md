# Docker

## Reminder about docker

Recall that **docker** is the de facto standard of containerization tools in IT. Containerization is a natural evolution of the idea of virtualizing application runtime environments to encapsulate all necessary dependencies in a separate entity. Originally, before containers, the problem of consistency of program execution, due to the configuration of environments and dependency versions, was solved by virtualizing several separate machines on the same computer. In other words, several virtual machines were created on the same physical machine. 

Virtual machines are emulation of a computer by software methods by allocating part of the resources of the host operating system - the real machine on which virtual machines run. It is important that allocating part of the resources means that they will no longer be available to the host until the virtual machine is disabled and destroyed! A virtual machine is an abstraction of a real machine, with all the implications of this, has virtual devices and, most importantly, a complete operating system with its own kernel. An entire operating system is a very complex program that takes a long time to run and requires a lot of resources. The host must have special software installed - a hypervisor (e.g. Virtualbox), which allocates part of the host resources for the needs of the operating systems of the virtual machines. This approach, as you can guess, turns out to be very, very costly. **Docker** and the idea of containerization in general solve these problems. Now separate containers, instead of virtual machines, use the host operating system kernel directly, do not require running a full OS, do not require a hypervisor, and also use host resources dynamically - exactly as much as is needed at the moment. It is easy to guess that containers are much faster to run, and can "stand up" on the same host in a much larger quantity than virtual machines. All of the above does not mean that **docker** containers are improved virtual machines or can safely be used instead. Virtual machines still perform their function, since **docker** containers give up many properties of real machines in order to achieve maximum lightness, respectively are not complete abstractions of real machines. At the very least, they don't have their own OS kernel, so you can't run a container with a Windows application on a Linux machine. MacOS automatically uses a virtual machine with the Linux kernel to run **docker** containers, so there is nothing to worry about.

**Docker** has a client-service architecture. Instead of a hypervisor, the service - **docker engine** - provides a REST api for creating and managing containers. 

Use `docker` in the console to view the available basic commands.

First of all, in order to run a container with some application, you need to create a *Dockerfile* and place it in the directory with the application. A *Dockerfile* is a text file with instructions that tell the **docker engine** service what *image* to create using that application. These instructions usually include:

1. The OS crippled version (corresponding to the host kernel, of course)
2. The runtime environment corresponding to the programming language in which the application is written (python, jre...)
3. Necessary libraries
4. Application files copied from the host
5. Environment variables

Usually, this information is enough to create a container image - a template by which containers with this application will be built. The image itself is not the target executable file, but the basis from which any **docker engine** can make a **docker** - container which is guaranteed to run the containerized application in the same way. The image can be added to the *registry* on the *docker hub* and used when running the container on any other machine in the same way as the source code and *github* or *gitlab*.

Available instructions for Dockerfile:

1. *FROM* - the image that will be inherited when the new image is created.
2. *WORKDIR* - working directory, all commands will be executed from this directory.
3. *COPY* and *ADD* allow you to add new files and directories inside the container.
4. *RUN* - commands that will be executed in bash when building the image.
5. *ENV* sets environment variables.
6. *EXPOSE* tells **docker engine** which port the container will listen on during runtime.
7. *USER* - the user with whose rights the commands are run.
8. *CMD* and *ENTRYPOINT* allow you to define the commands that will be executed in bash when you run the container

The *Dockerfile* consists of a sequence of instructions. Each instruction creates a separate *layer* - a set of modified files inside the image after applying the current instruction. All layers are cached, which allows to optimize image building, because layers without changes are simply taken out of the image cache. So it is important to remember *that instructions inside the Dockerfile must be in order from least likely to most likely to change files.* The most common flaw here is installing application dependencies and third-party project libraries after the source code has been fully copied. Then every small change in the code will cause the layer responsible for installing the libraries to be unable to be cached and will be executed every time the image is built. If, however, you first copy inside the image only those files needed to install the dependencies and only after installing them copy the source code of the program, the cached layer with dependencies will be able to be used correctly.

Container images have different tags - some names, usually indicating the version of the image. These can be either words or numbers. The reserved tag *latest* is created automatically, and is expected to indicate the latest version of the layer. 

Use the `docker image` command to get information about available image commands.

Usually a simple *Dockerfile* does the following:

1. Is inherited from some base image with an operating system and a preinstalled runtime environment (such an image can be found on **docker hub**) (command FROM)
2. Optionally creates a user so that the program is not executed "from root" (USER instruction)
3. Sets the remaining necessary dependencies (by RUN and possibly COPY instructions)
4. Copies the executable code of the program (COPY instruction)
5. Runs the application (CMD or ENTRYPOINT instruction)
6. Specifies the listening port (EXPOSE instruction)

Then, the container is built and run according to the created image. That's it! The program runs inside the container!
 
Use the `docker container` command to get information about available container commands.

But the most important advantage of **docker** containers is the fact that they can be shared very easily. There are two ways to do this:

1. Save the image as an archive (`docker save`command).
2. Use your repository on the docker hub (`docker push`command).

## Docker compose

Docker compose is a tool for managing multi-container applications. Accordingly, it allows you to run and configure the interaction of different application modules allocated into services. A service is a stable concept for a single containerizable entity. In this case, recall that docker compose is a tool separate from the standard docker engine package.

Normally, deploying a multi-container application involves the following points:

1. Writing a multiservice application (this point is already done!).
2. Writing a docker file for each separately containerized service.
3. Writing a compose file where each service is defined
4. Building and running containers with `docker-compose build`and `docker-compose up`commands

The Compose file is written in yaml format and has the following structure:

```yaml
version: "3.8"                  # docker compose version
services:                       # a block describing each individual service
    gateway:                    # the name of any service can be arbitrary, but usually reflects its essence
        build: "./gateway"      # the path of the Dockerfile of this service
        ports:                  # port mapping
            - 8080:8080         # host: container
        environment:            # environment variables list
            SHOP_URL: https://shop
            SHOP_PORT: 8081
        command: <shell cmd>    # some command that will run instead of the CMD Dockerfile. 
    shop:
        build: "./shop"
        <...>
    db:
        image: "postgres:15.1-alpine"   # instead of building a new image, you can use a ready-made public one, for example, for the database.
        volume:                         # volume is the memory outside the container for persistent data storage. Here the volume for that particular service is specified.
            - shop_db:/var/lib/postgresql/data
    <...>                       # there can be 
    any number of services  
volumes:                        # volume definition
    shop_db:   
```

Note that the names gateway, shop, and db will be resolved to the corresponding container host names when they are run through docker-compose. This happens because when you start containers with docker-compose, a new virtual network is created that contains as many hosts as the microservices defined in the docker-compose file. Also, this network includes a DNS-server, which is just responsible for mapping the names of services in the internal to this virtual network ip addresses.  

A common problem is when a dependent container is started before the container it depends on. For example, the database usually takes much longer to run than an ordinary service. In such a case, special `wait-for` shell scripts are used, which must be run before running the dependent application in a command or entrypoint. Such shell scripts are freely available, such as `docker compose wait for it shell script'.

Use`docker-compose`, `docker-compose build --help` and `docker-compose up --help` to get information about available options and commands.