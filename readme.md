# YuuKontainer
> Dockerized database container creation system + maven plugins.

# TLDR
- spawn in docker containers
- spawn them outside of tests!
- bring your own image or use ours
- friendly api, sane defaults
- convenient maven plugin
  
## What is this?
YuuKontainer is a library that lets you spawn docker containers directly from code, without having to worry about the semantics of where they're running or the anxiety of cleaning them up.

You can spawn containers from your production code, per unit test, for all unit tests, or even before other maven plugins with the kontainer-maven-plugin module

## How do I use this?
- Create a `TickFactory`, like so, and call `#produce()` on it to get a `Tick` library object:
  - add a new `HookResourceManager`,
  - add either a `WindowsSourceProvider` or `UnixSourceProvider`, depending on your test server's OS;
  - add a `DefaultPullStrategy`
- Call `tick.createContainer(new TinyImageTerms("redis"))`
- You now have a handle to a live running redis container!

```
Tick t = new CommonTickFactory(
    new HookResourceManager.Provider(false),
    <docker launch provider>,
    new DefaultPullStrategy.Provider()
).produce();

t.createContainer(new TinyImageTerms("redis"));
```

## Unix Info

If you want to use Kontainer on Unix (MacOS or Linux) make sure that Docker is set up so it does
not need the sudo command to run. Kontainer (And TestContainers) use console commands internally
to interact with Docker and if access to Docker is limited you will get an error like so:

```WARNING: Error loading config file: /home/user/.docker/config.json -
stat /home/user/.docker/config.json: permission denied```
```

You can fix this by following the instructions of the site here:
https://docs.docker.com/engine/install/linux-postinstall/

## I want to use this

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

## Inspirations
YuuKontainer was inspired by TestContainers; it was created as a lighter weight alternative that did not require reflection or the spawning containers for book-keeping, and especially aimed to be generalizable beyond testing Despite this, TestContainers is still a viable and quality alternative if you just need docker containers at test time.
