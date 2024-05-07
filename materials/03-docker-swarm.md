# Docker swarm


**Docker swarm** is an orchestration tool that comes along with *docker*. Orchestration is the process of managing different containers distributed between several nodes in the same cluster. Orchestration allows you to achieve:


- automatic balancing of containers between nodes;
- higher scalability of the system (connecting new nodes and load redistribution);
- restart of "failing" deployed software modules or *disaster recovery*, in other words - the orchestration tool maintains high *availability* of the system;
- security of traffic between different nodes within the cluster;
- and most importantly, it combines the entire complex application structure with a microservice architecture into some single entity with a single entry point, encapsulating all the complexity and interacting with the user in the same manner as a monolith.


Working with docker swarm is very easy. It is enough to divide nodes into two groups: managers and work nodes or *workers* and execute several commands.


Managers are the nodes that distribute tasks to worker nodes and ensure consistent state of their execution. The tasks themselves correspond to some services - replica sets of some docker image. There can be more than one replica of a single docker image. One task corresponds to one replica. Thus, managers distribute tasks to workers equal to the sum of the replicas of all running services, trying to balance them by workload. When given to a particular worker for execution, a container corresponding to the service image is started in that worker.


When nodes are combined into one swarm, communication between their docker-engines occurs via a special overlay network, which allows for correct orchestration. The overlay network driver creates a distributed network between the docker-engines. This network sort of overlay the host-specific networks, allowing the containers connected to it (including swarm service containers) to exchange data securely when encryption is enabled. Docker transparently handles the routing of each packet from and to each specific docker-engine host and each specific container.


In order to actualize a node as a manager, you must run the command `docker swarm init --advertise-addr [ip address of the machine to send to the overlay network]`. This will generate a join-token for the workers of this manager. This can be saved using the command `docker swarm join-token`.


To connect the worker to the manager you need to use the command: `docker swarm join --token [token] [manager's ip address]`.


In order to deploy all the services of an application at once, you can use the compose file and the command: `docker stack deploy`. However, since the task for each service can eventually be delegated to any worker, the images must be "reachable" from any node. That is, all the images must be loaded into some available docker registry (e.g. a personal registry in the docker hub), and then all the service images in the compose file must be replaced by those loaded into the selected docker registry.

