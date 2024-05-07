# Vagrant

**Vagrant** is a tool for automating the process of virtualizing environments. It is relatively easy to use and allows you to create multiple virtual machines with the specified parameters using a single configuration file and a few commands. The **Vagrant** itself does not create virtual machines, but uses *providers* - virtualization tools representing the api, for example - *virtualbox*.

Procedure of work with **Vagrant**:

0. Install vagrant, virtualbox, docker swarm.
1. First you need to create a working directory for **Vagrant**. This is usually the root of the project.
2. Next, a *Vagrantfile* is created using the `vagrant init` command.
3. The *Vagrantfile* creates the machines and specifies their names, operating systems, shell scripts which will be executed at the start of the machines to install the necessary tools and initialize the *docker swarm* (at least *docker*), and so on. The generated *Vagrantfile* contains the initial instructions for writing it. 
4. `vagrant up` - to run the machines.
5. `vagrant status` - machine status check.
6. Next, to "log in" to any of the machines, you must enter the command `vagrant ssh <machine name>`

