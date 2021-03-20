package me.aurium.tick.container;

import com.github.dockerjava.api.DockerClient;

public class ClientTickContainer implements TickContainer {

    private final DockerClient initializedClient;
    private final String containerIdentifier;
    private final int shutdownWait;

    public ClientTickContainer(DockerClient initializedClient, String containerIdentifier, ContainerOptions options) {
        this.initializedClient = initializedClient;
        this.containerIdentifier = containerIdentifier;
        this.shutdownWait = options.getContainerShutdownWait();
    }

    @Override
    public String containerID() {
        return containerIdentifier;
    }

    @Override
    public void start() {
        initializedClient.startContainerCmd(containerIdentifier).exec();
    }

    @Override
    public void stop() {
        //probably a good spot for things like cooloff
        initializedClient.stopContainerCmd(containerIdentifier).withTimeout(shutdownWait).exec();
    }

    @Override
    public void close() throws Exception {
        this.stop();
    }
}
