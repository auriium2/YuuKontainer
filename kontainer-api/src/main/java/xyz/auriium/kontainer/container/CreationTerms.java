package xyz.auriium.kontainer.container;

import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.PortBinding;
import xyz.auriium.kontainer.centralized.ResourceManager;
import xyz.auriium.kontainer.docker.source.DockerSource;

import java.util.Optional;

public interface CreationTerms<T extends TickContainer>  {

    String getDockerImageName();

    String[] getParameters();

    Optional<Bind[]> getBinds();

    Optional<PortBinding[]> getPortBindings();

    Optional<String[]> getCommands();

    String getContainerName();


    /**
     * Method used to describe the creation of the actual container
     *
     * Can also be used as a callback for post container startup
     *
     * @param location the dockersource used
     * @param dockerID the identifier of the container
     * @return a new container object
     */
    T instantiateHolder(DockerSource location, ResourceManager manager, String dockerID);
}
