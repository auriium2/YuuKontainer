package xyz.auriium.tick.centralized;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.container.CreationTerms;
import xyz.auriium.tick.container.TickContainer;
import xyz.auriium.tick.docker.image.PullStrategy;
import xyz.auriium.tick.docker.source.DockerSource;

import java.util.Arrays;

public class CommonTick implements Tick{

    private final Logger logger = LoggerFactory.getLogger("(TICK | MANAGER)");

    private final DockerSource location;
    private final DockerClient client;

    private final PullStrategy strategy;
    private final ResourceManager manager;

    public CommonTick(DockerSource location, PullStrategy strategy, ResourceManager manager) {
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

        terms.getBinding().ifPresent(config::withPortBindings);

        CreateContainerResponse response = client.createContainerCmd(terms.getDockerImageName())
                .withName(terms.getContainerName())
                .withHostName("tick")
                .withEnv(terms.getParameters())
                .withHostConfig(config)
                .exec();

        String id = response.getId();

        //manager.submitContainer(id,false);
        logger.info("Container created! Starting container now.");


        client.startContainerCmd(id).exec();

        //TEST
        CreateContainerResponse container
                = client.createContainerCmd("alpine:latest")
                .withName("control-variable")
                .withHostName("baeldung")
                .withEnv("MONGO_LATEST_VERSION=3.6")
                .withPortBindings(PortBinding.parse("9999:27017"))
                .withBinds(Bind.parse("/Users/baeldung/mongo/data/db:/data/db")).exec();

        client.startContainerCmd(container.getId()).exec();


        InspectContainerResponse response1 = client.inspectContainerCmd(id).exec();

        if (!response1.getState().getRunning()) {
            logger.error(response1.toString());
            throw new IllegalStateException("Executed container start command but container is not marked as started in docker! Including exception above.");
        }

        //manager.submitContainer(id,true);
        logger.info("Container has been started successfully!");

        return terms.instantiateHolder(location,manager,response.getId());
    }

    @Override
    public void stop() {
        logger.info("Stopping TickBox now! Goodnight!");
        manager.stop();
    }
}
