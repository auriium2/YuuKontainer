package me.aurium.tick.container.container;

import com.github.dockerjava.api.DockerClient;
import me.aurium.tick.container.ContainerOptions;
import me.aurium.tick.container.JDBCUrlBuilder;
import me.aurium.tick.container.terms.mariadb.JDBCConfig;
import me.aurium.tick.docker.source.DockerLocation;

public class MariaDBContainer implements JDBCContainer {

    private final DockerLocation location;
    private final DockerClient client;

    private final ContainerOptions options;
    private final String containerID;
    private final JDBCConfig config;

    public MariaDBContainer(DockerLocation location, DockerClient client, ContainerOptions options, String containerID, JDBCConfig config) {
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
                .withIP(location.getIp())
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
    public void close() throws Exception {
        client.stopContainerCmd(containerID).withTimeout(options.getContainerShutdownWait()).exec();
        client.removeContainerCmd(containerID).exec();
    }
}
