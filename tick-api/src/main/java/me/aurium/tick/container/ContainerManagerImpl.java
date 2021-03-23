package me.aurium.tick.container;

import com.github.dockerjava.api.DockerClient;
import me.aurium.tick.container.container.TickContainer;
import me.aurium.tick.container.terms.CreationTerms;
import me.aurium.tick.docker.source.DockerLocation;

import java.util.HashSet;
import java.util.Set;

public class ContainerManagerImpl implements ContainerManager {

    //might need to make things threaded in the future

    private final DockerLocation location;
    private final DockerClient client;
    private final Set<TickContainer> tickContainers;

    private final ContainerOptions options;

    public ContainerManagerImpl(DockerLocation location, DockerClient client, ContainerOptions options) {
        this.location = location;
        this.client = client;
        this.options = options;
        this.tickContainers = new HashSet<>(); //maybe concurrent stuff will make this worse in the future but for now as we are sync this is fine
    }

    @Override
    public void close() throws Exception {
        for (TickContainer container : tickContainers) {
            container.close(); //should closed containers remain in the set?
            // not the ones here but if a container is manually stopped. I think it should, but that's what reviews are for - another opinion
        }
    }

    @Override
    public <T extends TickContainer> T produceContainer(CreationTerms<T> terms) {

        T container = terms.creation(location,client,options);

        tickContainers.add(container);

        return container;
    }
}
