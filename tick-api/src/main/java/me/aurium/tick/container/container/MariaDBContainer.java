package me.aurium.tick.container.container;

import com.github.dockerjava.api.DockerClient;
import me.aurium.tick.container.ContainerOptions;
import me.aurium.tick.container.terms.MariaDBTerms;

public class MariaDBContainer implements JDBCContainer {

    private final DockerClient client;
    private final ContainerOptions options;
    private final String containerID;
    private final MariaDBTerms terms;

    public MariaDBContainer(DockerClient client, ContainerOptions options, String containerID, MariaDBTerms terms) {
        this.client = client;
        this.options = options;
        this.containerID = containerID;
        this.terms = terms;
    }

    @Override
    public String getJDBCUrl() {
        return null;
    }

    @Override
    public String getExposedJDBCUrl() {
        return null;
    }

    @Override
    public String managedContainerName() {
        return terms.getDockerName();
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
        //TODO a248 what you were saying about idempotcy: I believe this should be safe but i'm not 100% sure, do you
        //think it'd be best to just leave a check to see if the container is stopped? I think this command
        //fails safely if the container is already stopped, but i'm not sure.
        client.stopContainerCmd(containerID).withTimeout(options.getContainerShutdownWait()).exec();
    }

    @Override
    public void close() throws Exception {
        this.stop();
    }
}
