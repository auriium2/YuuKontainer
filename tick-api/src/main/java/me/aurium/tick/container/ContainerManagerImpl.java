package me.aurium.tick.container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;

import java.util.HashSet;
import java.util.Set;

public class ContainerManagerImpl implements ContainerManager {

    //might need to make things threaded in the future

    private final DockerClient client;
    private final Set<TickContainer> tickContainers;

    private final ContainerOptions options;

    public ContainerManagerImpl(DockerClient client, ContainerOptions options) {
        this.client = client;
        this.options = options;
        this.tickContainers = new HashSet<>(); //maybe  concurrent stuff will make this worse in the future but for now as we are sync this is fine
    }

    @Override
    public TickContainer produceContainer(CreationTerms terms) {
        CreateContainerResponse rep = terms.creation(client);

        TickContainer container = new ClientTickContainer(client, rep.getId(), options);

        tickContainers.add(container);

        return container;
    }

    @Override
    public void close() throws Exception {
        for (TickContainer container : tickContainers) {
            container.close(); //should closed containers remain in the set?
            // not the ones here but if a container is manually stopped. I think it should, but that's what reviews are for - another opinion
        }
    }
}
