package xyz.auriium.tick;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Version;
import xyz.auriium.tick.container.ContainerOptions;
import xyz.auriium.tick.docker.image.PullStrategyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.docker.source.*;

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
    public Tick produce() throws SourceProvideException {
        logger.info("(TICK) Attempting to produce a Tick! Producing DockerClient now...");

        DockerSource source = provider.source(clientOptions);
        DockerClient client = source.getClient();

        Version dockerVersion = client.versionCmd().exec();

        logger.info("(TICK) DockerClient startup successful!" +
                "API version: " + dockerVersion.getApiVersion() + "\n" +
                "Docker version: " + dockerVersion.getVersion() + "\n" +
                "OS: " + dockerVersion.getOperatingSystem());

        logger.info("(TICK) ContainerManager set up correctly! Tick is now ready for use!");

        return new CommonTick(source, client, strategy.getStrategy(client), containerOptions);
    }
}
