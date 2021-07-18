package xyz.auriium.tick.docker.image;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.centralized.ResourceManager;

import java.util.concurrent.TimeUnit;

public class CommonPoolStrategy implements PullStrategy {

    private final Logger logger = LoggerFactory.getLogger("(TICK | Cached Blocking Strategy)");

    private final DockerClient client;
    private final ResourceManager manager;

    public CommonPoolStrategy(DockerClient client, ResourceManager manager) {
        this.client = client;
        this.manager = manager;
    }

    @Override
    public boolean shouldLoad(String dockerImageName) {
        try {
            client.inspectImageCmd(dockerImageName).exec();
            return true;
        } catch (NotFoundException exception) {
            return false;
        }
    }

    @Override
    public void load(String dockerImageName) {

        logger.info("Attempting to pull image with name: " + dockerImageName);

        try {

            client.pullImageCmd(dockerImageName).exec(new LoggingPullResultCallback())
                    .awaitCompletion(30, TimeUnit.SECONDS);

            manager.submitImage(dockerImageName);

            logger.info("Image load finished!");
        } catch (InterruptedException exception) {
            logger.error("An exception occurred while loading an image: ", exception);
        }

    }

    public static class Provider implements PullStrategyProvider {
        @Override
        public CommonPoolStrategy provide(DockerClient client, ResourceManager manager) {
            return new CommonPoolStrategy(client, manager);
        }
    }

}
