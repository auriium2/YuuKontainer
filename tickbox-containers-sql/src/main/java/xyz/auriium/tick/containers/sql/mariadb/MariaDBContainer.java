package xyz.auriium.tick.containers.sql.mariadb;

import xyz.auriium.tick.centralized.ResourceManager;
import xyz.auriium.tick.container.JDBCUrlBuilder;
import xyz.auriium.tick.containers.sql.JDBCConfig;
import xyz.auriium.tick.containers.sql.JDBCContainer;
import xyz.auriium.tick.docker.source.DockerSource;

public class MariaDBContainer implements JDBCContainer {

    private final ResourceManager manager;
    private final DockerSource location;

    private final String containerID;
    private final JDBCConfig config;

    public MariaDBContainer(ResourceManager manager, DockerSource location, String containerID, JDBCConfig config) {
        this.manager = manager;
        this.location = location;
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
    public String containerName() {
        return config.getContainerName();
    }

    @Override
    public String containerID() {
        return containerID;
    }

    @Override
    public void destroy() {
        manager.destroyContainer(containerID);
    }

}
