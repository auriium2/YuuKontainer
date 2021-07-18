package xyz.auriium.tick.centralized;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.docker.source.DockerSource;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HookResourceManager implements ResourceManager { //toInterface

    private final Map<String,Boolean> containers = new ConcurrentHashMap<>();
    private final Set<String> images = ConcurrentHashMap.newKeySet();

    private final Logger logger = LoggerFactory.getLogger("(TICK | HookResourceManager)");
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
        this.containers.put(id,val);
    }

    @Override
    public void submitImage(String imageName) {
        this.images.add(imageName);
    }

    /**
     * Shuts down a container if it is not already shut down.
     * @param id the identification number of the container to destroy
     */
    @Override
    public void destroyContainer(String id) {
        boolean shutDown = this.containers.remove(id);

        if (shutDown) {

            logger.info("Stopping container: {}", id);
            dockerSource.getClient().killContainerCmd(id).exec();
            logger.info("Stopped container!");

        }

        logger.info("Removing container: {}", id);
        dockerSource.getClient().removeContainerCmd(id).withRemoveVolumes(true).withForce(true).exec();
        logger.info("Removed container and associated volume(s)!");
    }

    public void stop() {
        containers.forEach((str, aBoolean) -> destroyContainer(str));
    }

    public static class Provider implements ResourceManagerProvider {

        private final Logger logger = LoggerFactory.getLogger("(TICK | HookResourceProvider)");
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
