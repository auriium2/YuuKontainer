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
    private final CreationOptions creationOptions;
    private final ContainerOptions containerOptions;

    private final Logger logger = LoggerFactory.getLogger("(TICK | FACTORY)");

    /**
     * Create a new TickFactory that initializes Ticks with default specs
     * @param provider the docker source provider to use in order to get a DockerClient instance
     * @param strategy the pull strategy to use in order to handle pulling new images
     * @param creationOptions client creation options that pertain to how {@param provider} can be called
     * @param containerOptions options that are used once a client is created and a container is required.
     */
    public CommonTickFactory(DockerSourceProvider provider, PullStrategyProvider<?> strategy, CreationOptions creationOptions, ContainerOptions containerOptions) {
        this.provider = provider;
        this.strategy = strategy;
        this.creationOptions = creationOptions;
        this.containerOptions = containerOptions;
    }

    /**
     * Creates a new TickFactory with default settings
     * @param provider the docker source provider to use in order to get a DockerClient instance
     * @param strategy the pull strategy to use in order to handle pulling new images
     */
    public CommonTickFactory(DockerSourceProvider provider, PullStrategyProvider<?> strategy) {
        this(provider,strategy, CreationOptions.defaults(),ContainerOptions.defaults());
    }

    @Override
    public Tick produce() {
        logger.info("Initializing tick startup, performing DockerSourceProvider pre-check!");

        ApplicableResult result = provider.isApplicable();

        if (!result.isApplicable()) {
            logger.error(String.format("Attempt to check valid DockerClient using [%s] failed!", provider.name()));
            logger.error(String.format("Reason: [%s]", result.getReason()));
            throw new InvalidProviderException();
        }

        logger.info("Pre-check successful! Attempting to produce a DockerClient now.");

        DockerSource source = provider.source(creationOptions);
        DockerClient client = source.getClient();

        logger.info("Client produced successfully! Executing final startup activities...");

        //TODO test the dockersource - run multiple client commands and a test startup/shutdown on it
        if (creationOptions.isUsePostCreationTest()) {
            logger.debug("Test would occur here!");
        }

        Version dockerVersion = client.versionCmd().exec();

        logger.info("(DockerClient startup successful!" + "\n" +
                "API version: " + dockerVersion.getApiVersion() + "\n" +
                "Docker version: " + dockerVersion.getVersion() + "\n" +
                "OS: " + dockerVersion.getOperatingSystem());

        return new CommonTick(source, client, strategy.getStrategy(client), containerOptions);




    }
}
