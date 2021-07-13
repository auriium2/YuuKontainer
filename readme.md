# TickBox

A dockerized Database Container creation system + maven plugins.

# Description 

A tool meant for developers who really really don't want to use the mess that is TestContainers.

Comes in both code-based (tickbox-api) and maven plugin (tickbox-plugin) formats depending on 
your use case (Whether you want a single container for all tests or need multiple containers for 
multiple tests.)

# Warning

If you want to use TickBox on Unix (MacOS or Linux) make sure that Docker is set up so it does
not need the sudo command to run. TickBox (And TestContainers) use console commands internally
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
        <artifactId>tickbox-api</artifactId>
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

"TickBox" as in "Tick", a parasite that hooks onto something (docker) and "Box", as in a container.