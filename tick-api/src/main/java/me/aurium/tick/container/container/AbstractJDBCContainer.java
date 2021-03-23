package me.aurium.tick.container.container;

import com.github.dockerjava.api.DockerClient;
import me.aurium.tick.container.ContainerOptions;
import me.aurium.tick.container.terms.JDBCTerms;

public abstract class AbstractJDBCContainer implements JDBCContainer {

    private final DockerClient client;
    private final ContainerOptions options;
    private final String containerID;
    private final JDBCTerms terms;

    protected AbstractJDBCContainer(DockerClient client, ContainerOptions options, String containerID, JDBCTerms terms) {
        this.client = client;
        this.options = options;
        this.containerID = containerID;
        this.terms = terms;
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
        client.stopContainerCmd(containerID).withTimeout(options.getContainerShutdownWait()).exec();
    }

    @Override
    public void close() throws Exception {
        stop();
    }
}
