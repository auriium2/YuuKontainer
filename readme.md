# todo - remake this and stop bashing on testcontainers please
# Kontainer

A dockerized Database Container creation system + maven plugins.

# Description 

A tool meant for developers who really really don't want to use the mess that is TestContainers.

Comes in both code-based (Kontainer-api) and maven plugin (Kontainer-plugin) formats depending on 
your use case (Whether you want a single container for all tests or need multiple containers for 
multiple tests.)

# Warning

Kontainer is not TestContainers. It isn't really even meant to fill the same role that testcontainers fills.
Kontainer is a general purpose container creation library fit for use with JDBC. TestContainers is explicitly for testing.

While Kontainer is also very useful in the testing environment, Kontainer explicitly avoids hackery and "magic code"
that applications like TestContainers may implement in order to smoothen the testing experience.
In TestContainers, various reflective hacks are done in order to allow containers to stop at the end of a JUnit test.
A separate docker container is deployed just to make sure resources do not escape. (ryuk)

In TestContainers, I try to explicitly avoid magic code/hackery/static abuse while also offering you the option of choice.
TestContainers is brittle and static in design, and if you want to change something you'll have to dig deep into
the archaic, bloated source code and edit it in yourself. If you want to change something in Kontainer, write a new
implementation of a single interface, or change a value in a configuration object.

The biggest part of TestContainers for me that I attempt to give choice with is the ResourceReaper, 
or in our case, the ResourceManager. In TestContainers, it is a static part of the program 
that is essential, forcing Ryuk and runtime shutdown hooks down your throat. Here, you may choose
if you would like the HookResourceManager (Features both automatic closing of containers on jvm shutdown
as well as closing of containers and images when the main Kontainer instance is closed) or the EmptyResourceManager
(which allows you to manually and explicitly remove resources left behind)

# Unix Info

If you want to use Kontainer on Unix (MacOS or Linux) make sure that Docker is set up so it does
not need the sudo command to run. Kontainer (And TestContainers) use console commands internally
to interact with Docker and if access to Docker is limited you will get an error like so:

```WARNING: Error loading config file: /home/user/.docker/config.json -
stat /home/user/.docker/config.json: permission denied```
```

You can fix this by following the instructions of the site here:
https://docs.docker.com/engine/install/linux-postinstall/

A Docker-Machine module exists but is currently unusuable due to the various requirements to convince
DockerMachine to port-forward external port access to the internal docker container. Therefore, please
do not attempt to use the docker-machine strategy.


# Maven Info

```
<dependencies>
    <dependency>
        <groupId>xyz.auriium</groupId>
        <artifactId>kontainer-api</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>central-repo</id>
        <url>https://repo.auriium.xyz/releases</url>
    </repository>
</repositories>
```

"Kontainer" as in "Kontainer", a parasite that hooks onto something (docker) and "Box", as in a container.
