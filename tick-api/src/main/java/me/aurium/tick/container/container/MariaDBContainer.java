package me.aurium.tick.container.container;

import com.github.dockerjava.api.DockerClient;
import me.aurium.tick.container.ContainerOptions;
import me.aurium.tick.container.JDBCUrlBuilder;
import me.aurium.tick.container.terms.MariaDBTerms;
import me.aurium.tick.docker.source.DockerLocation;

public class MariaDBContainer implements JDBCContainer {

    private final DockerLocation location;
    private final DockerClient client;
    private final ContainerOptions options;
    private final String containerID;
    private final MariaDBTerms terms;

    public MariaDBContainer(DockerLocation location, DockerClient client, ContainerOptions options, String containerID, MariaDBTerms terms) {
        this.location = location;
        this.client = client;
        this.options = options;
        this.containerID = containerID;
        this.terms = terms;
    }

    @Override
    public String getJDBCUrl() {
        return new JDBCUrlBuilder()
                .withDBName(terms.databaseName())
                .withDriver("mariadb")
                .withIP(location.getIp())
                .withPort(terms.externalPort()).build();
    }

    @Override
    public String managedContainerName() {
        return terms.containerName();
    }

    @Override
    public String containerID() {
        return containerID;
    }

    @Override
    public void start() {
        client.startContainerCmd(containerID).exec();
    }

    @Override
    public void stop() {
        //TODO crawl the docs because i can't find anything on idempotcy there @a248

        client.stopContainerCmd(containerID).withTimeout(options.getContainerShutdownWait()).exec();
    }

    @Override
    public void close() throws Exception {
        this.stop();
    }
}
