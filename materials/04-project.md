# Multiservice application example


You can find an example of a multiservice application in the `./src/` folder . Below is its conceptual diagram.


<img src="misc/images/project_diagram.png"  width="800">


The project is written in java (jdk8), but you do not need to know it to deploy it in docker containers. It is enough to know what dependencies it needs and how the individual services relate to each other.


So, the application itself is a room booking service. Or rather its backend. It consists of nine parts (services in docker-compose terminology):


1. Postgres database.
2. Message queue - rabbitmq.
3. Session service - user sessions manager service.
4. Hotel service - hotel entities manager service.
5. Payment service - payment manager service.
6. Loyalty service - loyalty program manager service.
7. Report service - statistic collector service.
8. Booking service - reservation manager service.
9. Gateway service - facade for interaction with the rest of the microservices


Let's start with the first two services. For rabbitmq it is best to use the standard image, since no additional configuration is needed (e.g - rabbitmq:3-management-alpine).


Population with the initial values of the postgres databases is done automatically by the corresponding services, but the databases themselves must be created by running the script `src\services\database\init.sql`


In the case of the other services, you have to work a little harder. There are several options here, but we will consider only two of them.


## 1. Local build of jar packages


Applications written in java are executed in a special virtual machine - jvm. The applications themselves are packaged as jar files which contain the program code itself and all the necessary dependencies. Thus, the most trivial way to run a java program would be to build the project locally (in this case using the package manager maven and its wrapper mvnw, which is in the folder with each servcion) by running the command `./mvnw package -DskipTests`. The first build of the first project may take a long time. After that the resulting jar file in the generated target folder will be the executable file.


Now, in the Dockerfile on the base, for example, `openjdk:8-jdk-alpine`, it is sufficient to specify instructions to copy the built project and run it with the command `java -jar target/*.jar`.


P.S.: It is important to remember here that most services require an already deployed service with postgres to start correctly, so don't forget about the `wait-for-it.sh` script.


P.P.S.: It is important to specify the exact tag of the base image to avoid using the frequently changed `latest` tag. *Public commonly used images do not change.*


## 2. Building inside docker


The problems of the previous approach are obvious if you try this option. Too much manual work. So we move on to building inside docker.


To do this, you can create a working directory inside the image and move all the files needed for the build there, and then build it.


Moreover, the maven manager supports a separate dependencies connection, which is the longest process in the build (separating this step from the build is an optimization based on the nature of the docker image layers). All dependencies are contained in a separate file, `pom.xml`, so the plan for the new Dockerfile is as follows:


1. Create a working directory.
2. Import the maven wrapper and `pom.xml`.
3. Install the project dependencies with the command: `./mvnw dependency:go-offline`.
4. Copy the project source code
5. Build the project in the same way as the previous approach or run it with `./mvnw spring-boot:run`


P.S.: to reduce the size of the final image, the multi-stage build approach can be used, since not all of the files used in the build are needed at runtime.


## What needs to be considered


Services in java expect a certain set of environment variables:


### Session service


- POSTGRES_HOST: <database host>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (may differ)
- POSTGRES_PASSWORD: "postgres" (may differ)
- POSTGRES_DB: users_db




### Hotel service


- POSTGRES_HOST: <database host>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (may differ)
- POSTGRES_PASSWORD: "postgres" (may differ)
- POSTGRES_DB: hotels_db


### Payment service


- POSTGRES_HOST: <database host>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (may differ)
- POSTGRES_PASSWORD: "postgres" (may differ)
- POSTGRES_DB: payments_db


### Loyalty service


- POSTGRES_HOST: <database host>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (may differ)
- POSTGRES_PASSWORD: "postgres" (may differ)
- POSTGRES_DB: balances_db


### Report service


- POSTGRES_HOST: <database host>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (may differ)
- POSTGRES_PASSWORD: "postgres" (may differ)
- POSTGRES_DB: statistics_db
- RABBIT_MQ_HOST: <host queue>
- RABBIT_MQ_PORT: 5672
- RABBIT_MQ_USER: guest
- RABBIT_MQ_PASSWORD: guest
- RABBIT_MQ_QUEUE_NAME: messagequeue
- RABBIT_MQ_EXCHANGE: messagequeue-exchange


### Booking service


- POSTGRES_HOST: <database host>
- POSTGRES_PORT: 5432
- POSTGRES_USER : postgres (may differ)
- POSTGRES_PASSWORD: "postgres" (may differ)
- POSTGRES_DB: reservations_db
- RABBIT_MQ_HOST: <host queue>
- RABBIT_MQ_PORT: 5672
- RABBIT_MQ_USER: guest
- RABBIT_MQ_PASSWORD: guest
- RABBIT_MQ_QUEUE_NAME: messagequeue
- RABBIT_MQ_EXCHANGE: messagequeue-exchange
- HOTEL_SERVICE_HOST: <hotel service host >
- HOTEL_SERVICE_PORT: 8082
- PAYMENT_SERVICE_HOST: <payment service host>
- PAYMENT_SERVICE_PORT: 8084
- LOYALTY_SERVICE_HOST: <loyalty service host>
- LOYALTY_SERVICE_PORT: 8085


### Gateway service


- SESSION_SERVICE_HOST: <session service host>
- SESSION_SERVICE_PORT: 8081
- HOTEL_SERVICE_HOST: <hotel service host>
- HOTEL_SERVICE_PORT: 8082
- BOOKING_SERVICE_HOST: <booking service host>
- BOOKING_SERVICE_PORT: 8083
- PAYMENT_SERVICE_HOST: <payment service host>
- PAYMENT_SERVICE_PORT: 8084
- LOYALTY_SERVICE_HOST: <loyalty service host>
- LOYALTY_SERVICE_PORT: 8085
- REPORT_SERVICE_HOST: <report service host>
- REPORT_SERVICE_PORT: 8086


Services are open on the corresponding local ports:


- Session service - 8081
- Hotel service - 8082
- Booking service - 8083
- Payment service - 8084
- Loyalty service - 8085
- Report service - 8086
- Gateway service - 8087



