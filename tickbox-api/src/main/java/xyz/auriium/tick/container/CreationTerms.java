package xyz.auriium.tick.container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.PortBinding;
import xyz.auriium.tick.centralized.ResourceManager;
import xyz.auriium.tick.docker.source.DockerSource;

public interface CreationTerms<T extends TickContainer>  {

    String getDockerImageName();

    String[] getParameters();

    PortBinding getBinding();

    String getContainerName();

    /**
     * Method used to describe the creation of the actual container
     *
     * @param location the dockersource used
     * @param dockerID the identifier of the container
     * @return a new container object
     */
    T instantiateHolder(DockerSource location, ResourceManager manager, String dockerID);
}
