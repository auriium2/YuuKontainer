# YuuKontainer
> Dockerized database container creation system + maven plugins.

# TLDR
- spawn in docker containers
- bring your own image or use ours
- friendly api, sane defaults
- convenient maven plugin
  
## What is this?
YuuKontainer is a library that lets you spawn docker containers directly from code, without having to worry about the semantics of where they're running or the anxiety of cleaning them up.

You can spawn containers from your production code, per unit test, for all unit tests, or even before other maven plugins with the kontainer-maven-plugin module

# Warning

Kontainer is not TestContainers. It isn't meant to fill the same role that testcontainers fills.
Kontainer is a general purpose container creation library fit for use with JDBC. TestContainers shines in it's stated purpose: using docker containers for testing.

While Kontainer is also very useful in the testing environment, Kontainer explicitly avoids hackery and "magic code"
that applications like TestContainers may implement in order to smoothen the testing experience.
In TestContainers, various reflective hacks are done in order to allow containers to stop at the end of a JUnit test.
A separate docker container is deployed just to make sure resources do not escape. (ryuk)

In Kontainer, I try to explicitly avoid magic code and also offering you the option of choice.
TestContainers is a product of design requirements far beyond the scale that I could conceivably handle, and capably handles many more situations than Kontainer can. However, the same design requirements can lead to difficulty when you need to deal with container after-effects and other semantics. If you want to change something in Kontainer, it is possible to write a new implementation of a single interface, or change a value in a configuration object.

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
