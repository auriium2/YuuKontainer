package xyz.auriium.tick.centralized;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.container.CreationOptions;
import xyz.auriium.tick.docker.InvalidProviderException;
import xyz.auriium.tick.docker.image.PullStrategyProvider;
import xyz.auriium.tick.docker.source.*;

/**
 * Common implementation of the tick factory
 */
public class CommonTickFactory implements TickFactory{

    private final DockerSourceProvider sourceProvider;
    private final PullStrategyProvider strategyProvider;
    private final ResourceManagerProvider resourceManager;
    private final CreationOptions creationOptions;

    private final Logger logger = LoggerFactory.getLogger("(TICK | FACTORY)");

    /**
     * Create a new TickFactory that initializes Ticks with default specs
     * @param sourceProvider the docker source provider to use in order to get a DockerClient instance
     * @param strategyProvider the pull strategy to use in order to handle pulling new images
     * @param resourceManager the resource manager which handles destroying docker containers on tick shutdown.
     *                        If you do not want to use one of these, use the {@link EmptyResourceManager}
     * @param creationOptions client creation options that pertain to how {@param provider} can be called
     */
    public CommonTickFactory(DockerSourceProvider sourceProvider, PullStrategyProvider strategyProvider, ResourceManagerProvider resourceManager, CreationOptions creationOptions) {
        this.sourceProvider = sourceProvider;
        this.strategyProvider = strategyProvider;
        this.resourceManager = resourceManager;
        this.creationOptions = creationOptions;
    }

    /**
     * Creates a new TickFactory with default settings
     * @param manager the resource manager to use. If you are looking for the suggested default, use {@link HookResourceManager.Provider}
     *                However, if you want a resource manager that does not use a shutdown hook in order
     *                to produce a maven plugin, use {@link EmptyResourceManager.Provider}
     *
     * @param sourceProvider the docker source provider to use in order to get a DockerClient instance
     * @param strategyProvider the pull strategy to use in order to handle pulling new images
     */
    public CommonTickFactory(ResourceManagerProvider manager, DockerSourceProvider sourceProvider, PullStrategyProvider strategyProvider) {
        this(sourceProvider, strategyProvider, manager, CreationOptions.defaults());
    }

    @Override
    public Tick produce() {
        logger.info("Initializing tick startup, performing DockerSourceProvider pre-check!");

        ApplicableResult result = sourceProvider.isApplicable();

        if (!result.isApplicable()) {
            logger.error(String.format("Attempt to check valid DockerClient using [%s] failed!", sourceProvider.name()));
            logger.error(String.format("Reason: [%s]", result.getReason()));
            throw new InvalidProviderException();
        }

        logger.info("Pre-check successful! Attempting to produce a DockerClient now.");

        DockerSource source = sourceProvider.source(creationOptions);
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

        logger.info("Starting up resource manager implementation!");

        ResourceManager manager = resourceManager.make(source);

        logger.info("Resource manager online!");

        return new CommonTick(source, strategyProvider.provide(client, manager), manager);




    }
}
