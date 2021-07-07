Dockerized Database Container creation system + maven plugins.

Meant for developers who really really don't want to use the mess that is TestContainers.

"TickBox" as in "Tick", a parasite that hooks onto something (docker) and "Box", as in a container.
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