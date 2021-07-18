package xyz.auriium.tick.centralized;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.container.CreationTerms;
import xyz.auriium.tick.container.TickContainer;
import xyz.auriium.tick.docker.image.PullStrategy;
import xyz.auriium.tick.docker.source.DockerSource;

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

        strategy.shouldLoad(terms.getDockerImageName());

        logger.info("Initializing container with image: " + terms.getDockerImageName() + "! Assuming image is present!");

        CreateContainerResponse response = client.createContainerCmd(terms.getDockerImageName())
                .withName(terms.getContainerName())
                .withEnv(terms.getParameters())
                .withHostConfig(new HostConfig()
                        .withPortBindings(terms.getBinding())
                )
                .exec();

        String id = response.getId();

        manager.submitContainer(id,false);
        logger.info("Container created! Starting container now.");


        client.startContainerCmd(response.getId()).exec();
        manager.submitContainer(id,true);
        logger.info("Container has been started successfully!");

        return terms.instantiateHolder(location,manager,response.getId());
    }

    @Override
    public void stop() {
        manager.stop();
    }
}
