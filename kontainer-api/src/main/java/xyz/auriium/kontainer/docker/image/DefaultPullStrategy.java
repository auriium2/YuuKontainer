package xyz.auriium.kontainer.docker.image;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.kontainer.centralized.ResourceManager;

import java.util.concurrent.TimeUnit;

public class DefaultPullStrategy implements PullStrategy {

    private final Logger logger = LoggerFactory.getLogger("(TICK | DefaultPullStrategy)");

    private final DockerClient client;
    private final ResourceManager manager;

    public DefaultPullStrategy(DockerClient client, ResourceManager manager) {
        this.client = client;
        this.manager = manager;
    }

    @Override
    public boolean shouldLoad(String dockerImageName) {

        logger.debug("Testing whether image should be loaded...");

        try {
            client.inspectImageCmd(dockerImageName).exec();

            logger.debug("Image is present!");

            return false;
        } catch (NotFoundException exception) {

            logger.debug("Image is not present!");

            return true;
        }
    }

    @Override
    public void load(String dockerImageName) {

        logger.info("Attempting to pull image with name: " + dockerImageName);

        try {

            client.pullImageCmd(dockerImageName).exec(new DefaultPullCallback(logger))
                    .awaitCompletion(30, TimeUnit.SECONDS);

            manager.submitImage(dockerImageName);

            logger.info("Image load finished!");
        } catch (InterruptedException exception) {
            logger.error("An exception occurred while loading an image: ", exception);
        }

    }

    public static class Provider implements PullStrategyProvider {
        @Override
        public DefaultPullStrategy provide(DockerClient client, ResourceManager manager) {
            return new DefaultPullStrategy(client, manager);
        }
    }

}
