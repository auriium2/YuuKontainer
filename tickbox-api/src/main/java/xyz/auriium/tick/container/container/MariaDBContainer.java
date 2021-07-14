package xyz.auriium.tick.container.container;

import com.github.dockerjava.api.DockerClient;
import xyz.auriium.tick.container.ContainerOptions;
import xyz.auriium.tick.container.JDBCUrlBuilder;
import xyz.auriium.tick.container.terms.mariadb.JDBCConfig;
import xyz.auriium.tick.docker.source.DockerSource;

public class MariaDBContainer implements JDBCContainer {

    private final DockerSource location;
    private final DockerClient client;

    private final ContainerOptions options;
    private final String containerID;
    private final JDBCConfig config;

    public MariaDBContainer(DockerSource location, DockerClient client, ContainerOptions options, String containerID, JDBCConfig config) {
        this.location = location;
        this.client = client;
        this.options = options;
        this.containerID = containerID;
        this.config = config;
    }

    @Override
    public String getJDBCUrl() {
        return new JDBCUrlBuilder()
                .withDBName(config.getDatabaseName())
                .withDriver("mariadb")
                .withIP(location.getSourceHost())
                .withPort(config.getPortBinding()).build();
    }

    @Override
    public String managedContainerName() {
        return config.getContainerName();
    }

    @Override
    public String containerID() {
        return containerID;
    }

    @Override
    public void close() {
        client.stopContainerCmd(containerID).withTimeout(options.getContainerShutdownWait()).exec();
        client.removeContainerCmd(containerID).exec();
    }
}
