package me.aurium.tick;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Version;
import me.aurium.tick.container.ContainerManager;
import me.aurium.tick.container.ContainerManagerImpl;
import me.aurium.tick.container.ContainerOptions;
import me.aurium.tick.docker.DockerLocationImpl;
import me.aurium.tick.docker.image.PullStrategyProvider;
import me.aurium.tick.docker.source.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonTickFactory implements TickFactory{

    private final DockerSourceProvider provider;
    private final PullStrategyProvider<?> strategy;
    private final ClientOptions clientOptions;
    private final ContainerOptions containerOptions;

    private final Logger logger = LoggerFactory.getLogger(CommonTickFactory.class);

    //TODO builder
    public CommonTickFactory(DockerSourceProvider provider, PullStrategyProvider<?> strategy, ClientOptions clientOptions, ContainerOptions containerOptions) {
        this.provider = provider;
        this.strategy = strategy;
        this.clientOptions = clientOptions;
        this.containerOptions = containerOptions;
    }

    @Override
    public Tick produce() {
        logger.info("(TICK) Attempting to produce a Tick! Producing DockerClient now...");

        DockerSource source = provider.source().orElseThrow(InvalidProviderException::new);

        //ensure accurcy

        DockerLocation location = new DockerLocationImpl(source.getSourceIP(), source.getSourceURL(), source.getSSLConfig()); //cache
        DockerClient client = new DockerClientProducer(location).produce(clientOptions);

        Version dockerVersion = client.versionCmd().exec();

        logger.info("(TICK) DockerClient startup successful!" +
                "API version: " + dockerVersion.getApiVersion() + "\n" +
                "Docker version: " + dockerVersion.getVersion() + "\n" +
                "OS: " + dockerVersion.getOperatingSystem());

        logger.info("(TICK) Setting up a ContainerManager...");

        ContainerManager manager = new ContainerManagerImpl(location, client, strategy.getStrategy(client), containerOptions);

        logger.info("(TICK) ContainerManager set up correctly! Tick is now ready for use!");

        return new CommonTick(manager);
    }
}
