package me.aurium.tick.container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.HostConfig;
import me.aurium.tick.container.container.TickContainer;
import me.aurium.tick.container.terms.CreationTerms;
import me.aurium.tick.container.terms.termParts.Arguments;
import me.aurium.tick.container.terms.termParts.ArgumentsObject;
import me.aurium.tick.docker.source.DockerLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ContainerManagerImpl implements ContainerManager {

    //might need to make things threaded in the future

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DockerLocation location;
    private final DockerClient client;

    private final ContainerOptions options;

    public ContainerManagerImpl(DockerLocation location, DockerClient client, ContainerOptions options) {
        this.location = location;
        this.client = client;
        this.options = options;
    }

    @Override
    public <T extends TickContainer> T startupContainer(CreationTerms<T> termActual) throws InterruptedException {
        Arguments terms = termActual.creationArguments();

        logger.info("(TICK) Attempting to pull!");

        client.pullImageCmd(terms.getDockerImageName()).exec(new PullImageResultCallback())
                .awaitCompletion(30,TimeUnit.SECONDS); //blocking

        logger.info("(TICK) pull finished!");

        logger.info(client.versionCmd().exec().getVersion());

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
