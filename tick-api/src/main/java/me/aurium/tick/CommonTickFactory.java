package me.aurium.tick;

import com.github.dockerjava.api.DockerClient;
import me.aurium.tick.container.ContainerManager;
import me.aurium.tick.container.ContainerManagerImpl;
import me.aurium.tick.container.ContainerOptions;
import me.aurium.tick.docker.DockerLocationImpl;
import me.aurium.tick.docker.source.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonTickFactory implements TickFactory{

    private final DockerSourceProvider provider;
    private final ClientOptions clientOptions;
    private final ContainerOptions containerOptions;

    private final Logger logger = LoggerFactory.getLogger(CommonTickFactory.class);

    //TODO builder
    public CommonTickFactory(DockerSourceProvider provider, ClientOptions clientOptions, ContainerOptions containerOptions) {
        this.provider = provider;
        this.clientOptions = clientOptions;
        this.containerOptions = containerOptions;
    }

    @Override
    public Tick produce() {
        logger.debug("Attempting to produce a Tick! Producing DockerClient now...");

        DockerSource source = provider.source().orElseThrow(InvalidProviderException::new);
        DockerLocation location = new DockerLocationImpl(source.getSourceIP(), source.getSourceURL()); //cache
        DockerClient client = new DockerClientProducer(location).produce(clientOptions);

        logger.debug("DockerClient successfully initialized, setting up a ContainerManager...");

        ContainerManager manager = new ContainerManagerImpl(location, client,containerOptions);

        logger.debug("ContainerManager set up correctly! Tick is now ready for use!");

        return new CommonTick(manager);
    }
}
