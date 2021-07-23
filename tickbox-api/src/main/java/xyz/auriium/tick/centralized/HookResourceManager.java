package xyz.auriium.tick.centralized;

import com.github.dockerjava.api.command.InspectContainerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.docker.source.DockerSource;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HookResourceManager implements ResourceManager { //toInterface

    private final Map<String,Boolean> containers = new ConcurrentHashMap<>();
    private final Set<String> images = ConcurrentHashMap.newKeySet();

    private final Logger logger = LoggerFactory.getLogger("(TICK | RESOURCE MANAGER)");
    private final DockerSource dockerSource;

    HookResourceManager(DockerSource dockerSource) {
        this.dockerSource = dockerSource;
    }

    /**
     * Sets the state of a container into the manager
     * @param id the id of the container
     * @param val true if the container is started, false if it is not.
     */
    @Override
    public void submitContainer(String id, boolean val) {
        logger.debug("Added container with ID: " + id + " to manager!");
        this.containers.put(id,val);
    }

    @Override
    public void submitImage(String imageName) {
        logger.debug("Added image with name: " + imageName + " to manager!");
        this.images.add(imageName);
    }

    @Override
    public void destroyImage(String imageName) {
        logger.info("Removing image: {}", imageName);
        dockerSource.getClient().removeImageCmd(imageName);
        logger.debug("Removed image!");
    }

    /**
     * Shuts down a container if it is not already shut down.
     * @param id the identification number of the container to destroy
     */
    @Override
    public void destroyContainer(String id) {
        boolean running = this.containers.remove(id);

        InspectContainerResponse.ContainerState state = dockerSource.getClient().inspectContainerCmd(id).exec().getState();

        if (!state.getRunning() && running) {
            logger.error("Container is marked as running in ResourceManager yet docker says it is not running! Please report this to tickbox!");
            logger.error("Attempting to remove container anyways");
        } else if (running) {
            logger.info("Stopping container: {}", id);
            dockerSource.getClient().killContainerCmd(id).exec();
            logger.debug("Stopped container!");
        }

        logger.info("Removing container: {}", id);
        dockerSource.getClient().removeContainerCmd(id).withRemoveVolumes(true).withForce(true).exec();
        logger.debug("Removed container and associated volume(s)!");
    }

    public void stop() {
        containers.keySet().forEach(this::destroyContainer);
        images.forEach(this::destroyImage);
    }

    public static class Provider implements ResourceManagerProvider {

        private final Logger logger = LoggerFactory.getLogger("(TICK | RESOURCE PROVIDER)");
        private final boolean useShutdownHook;

        public Provider(boolean useShutdownHook) {
            this.useShutdownHook = useShutdownHook;
        }

        @Override
        public ResourceManager make(DockerSource source) {
            ResourceManager manager = new HookResourceManager(source);

            if (useShutdownHook) {
                logger.info("Attempting to attach shutdown hook now!");
                Runtime.getRuntime().addShutdownHook(new Thread(manager::stop));
            }

            return manager;
        }
    }



}
