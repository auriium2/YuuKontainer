package xyz.auriium.tick;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.container.ContainerOptions;
import xyz.auriium.tick.container.container.TickContainer;
import xyz.auriium.tick.container.terms.CreationTerms;
import xyz.auriium.tick.container.terms.termParts.Arguments;
import xyz.auriium.tick.docker.image.PullStrategy;
import xyz.auriium.tick.docker.source.DockerSource;

public class CommonTick implements Tick{

    private final Logger logger = LoggerFactory.getLogger("(TICK | MANAGER)");

    private final DockerSource location;
    private final DockerClient client;

    private final PullStrategy strategy;
    private final ContainerOptions options;

    public CommonTick(DockerSource location, DockerClient client, PullStrategy strategy, ContainerOptions options) {
        this.location = location;
        this.client = client;
        this.strategy = strategy;
        this.options = options;
    }

    @Override
    public <T extends TickContainer> T startupContainer(CreationTerms<T> termActual) throws InterruptedException {
        Arguments terms = termActual.creationArguments();

        if (strategy.shouldLoad(terms.getDockerImageName())) {
            logger.info("Attempting to pull image with name: " + terms.getDockerImageName());

            strategy.loadBlocking(terms.getDockerImageName()); //todo

            logger.info("Image load finished!");
        }

        logger.info("Initializing container with image: " + terms.getDockerImageName() + "! Assuming image is present!");

        CreateContainerResponse response = client.createContainerCmd(terms.getDockerImageName())
                .withName(terms.getCreationName())
                .withEnv(terms.getParameters())
                .withHostConfig(new HostConfig()
                        .withPortBindings(terms.getBinding())
                )
                .exec();

        client.startContainerCmd(response.getId()).exec();

        return termActual.creation(location,client,options,response.getId());
    }
}
