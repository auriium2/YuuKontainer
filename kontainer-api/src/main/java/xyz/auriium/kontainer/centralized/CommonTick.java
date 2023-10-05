package xyz.auriium.kontainer.centralized;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.exception.BadRequestException;
import com.github.dockerjava.api.exception.ConflictException;
import com.github.dockerjava.api.model.HostConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.kontainer.container.CreationTerms;
import xyz.auriium.kontainer.container.TickContainer;
import xyz.auriium.kontainer.docker.image.PullStrategy;
import xyz.auriium.kontainer.docker.source.DockerSource;

import java.io.IOException;
import java.util.Arrays;

public class CommonTick implements Tick{

    private final Logger logger = LoggerFactory.getLogger("(TICK | MANAGER)");

    private final DockerSource location;
    private final DockerClient client;

    private final PullStrategy strategy;
    private final ResourceManager manager;

    CommonTick(DockerSource location, PullStrategy strategy, ResourceManager manager) {
        this.location = location;
        this.client = location.getClient();
        this.strategy = strategy;
        this.manager = manager;
    }

    @Override
    public <T extends TickContainer> T createContainer(CreationTerms<T> terms) {

        strategy.loadIfRequired(terms.getDockerImageName());


        logger.info("Initializing container!");
        logger.info("Using image: {}", terms.getDockerImageName());
        logger.info("Using container name: {}", terms.getContainerName());
        logger.info("Using params: {}", Arrays.toString(terms.getParameters()));

        HostConfig config = new HostConfig();
        terms.getPortBindings().ifPresent(config::withPortBindings);
        terms.getBinds().ifPresent(config::withBinds);

        CreateContainerCmd cmd = client.createContainerCmd(terms.getDockerImageName())
                .withHostName("tick")
                .withName(terms.getContainerName())
                .withEnv(terms.getParameters())
                .withHostConfig(config);

        terms.getCommands().ifPresent(cmd::withCmd);


        CreateContainerResponse response = execDirtyHandlingException(cmd);

        String id = response.getId();

        manager.submitContainer(id,false);
        logger.info("Container created! Starting container now.");

        try {
            client.startContainerCmd(id).exec();
        } catch (BadRequestException exception) {

            //time to remove
            logger.error("Error during startup of container. Exiting early. Printing stacktrace below.");
            manager.destroyContainer(id);
            logger.error("Removed bad container. Exiting now! Sorry!");

            throw new IllegalStateException("An exception occurred while trying to start the container: " + exception);
        }


        InspectContainerResponse response1 = client.inspectContainerCmd(id).exec();

        if (!response1.getState().getRunning()) {
            logger.error(response1.toString());
            throw new IllegalStateException("Executed container start command but container is not marked as started in docker! Including exception above.");
        }

        manager.submitContainer(id,true);
        logger.info("Container has been started successfully!");

        return terms.instantiateHolder(location,manager,response.getId());


    }

    @Override
    public DockerSource expose() {
        return location;
    }

    //this all sucks i hate it
    //A try/catch is required here in order for container to not persist and be effectively removed.
    //all of this is hacky and gross so if anyone finds a better way please make a PR
    CreateContainerResponse execDirtyHandlingException(CreateContainerCmd cmd) {
        try {
            return cmd.exec();
        } catch (ConflictException e) {
            logger.error("Error during execution: Two containers of the same name exist. Destroying container and exiting early. Printing stacktrace below:");

            return getCreateContainerResponse(e, e.getMessage());
        }
    }

    //destroy an invalid container
    CreateContainerResponse getCreateContainerResponse(ConflictException e2, String message) {
        String s = e2.getMessage();

        s = s.substring(s.indexOf("r \\\"") + 4);
        s = s.substring(0, s.indexOf("\\\"."));

        manager.destroyContainer(s);
        logger.error("Removed bad container. Exiting now! Sorry!");

        throw new IllegalStateException(message);
    }

    @Override
    public void stop() {
        logger.info("Stopping TickBox now! Goodnight!");

        manager.stop();

        try {
            client.close();
        } catch (IOException e) {
            throw new ShutdownException("An exception occurred while closing the connection to DockerClient: " + e);
        }
    }
}
