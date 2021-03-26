package me.aurium.tick.container;

import com.github.dockerjava.api.DockerClient;
import me.aurium.tick.container.container.TickContainer;
import me.aurium.tick.container.terms.CreationTerms;

public interface ContainerManager extends AutoCloseable {

    //TODO needs to be some kind of guaruntee that this image exists on the local client, if not, it needs to be accessed
    <T extends TickContainer> T produceContainer(CreationTerms<T> terms);

    DockerClient debugClient(); //TODO remove

}
