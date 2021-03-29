package me.aurium.tick.container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.HostConfig;
import me.aurium.tick.container.container.TickContainer;
import me.aurium.tick.container.terms.CreationTerms;
import me.aurium.tick.container.terms.termParts.Arguments;
import me.aurium.tick.docker.image.PullStrategy;
import me.aurium.tick.docker.source.DockerLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ContainerManagerImpl implements ContainerManager {

    //might need to make things threaded in the future

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DockerLocation location;
    private final DockerClient client;

    private final PullStrategy strategy;
    private final ContainerOptions options;

    public ContainerManagerImpl(DockerLocation location, DockerClient client, PullStrategy strategy, ContainerOptions options) {
        this.location = location;
        this.client = client;
        this.strategy = strategy;
        this.options = options;
    }

    @Override
    public <T extends TickContainer> T startupContainer(CreationTerms<T> termActual) throws InterruptedException {
        Arguments terms = termActual.creationArguments();

        if (strategy.shouldLoad(terms.getDockerImageName())) {
            logger.info("(TICK) Attempting to pull image with name: " + terms.getDockerImageName());

            strategy.loadBlocking(terms.getDockerImageName());

            logger.info("(TICK) image load finished!");
        }

        logger.info("(TICK) Initializing container with image: " + terms.getDockerImageName() + "! Assuming image is present!");

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
