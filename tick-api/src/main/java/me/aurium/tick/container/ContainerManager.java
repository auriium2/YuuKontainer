package me.aurium.tick.container;

import com.github.dockerjava.api.DockerClient;
import me.aurium.tick.container.container.TickContainer;
import me.aurium.tick.container.terms.CreationTerms;

import java.util.concurrent.CompletableFuture;

public interface ContainerManager {

    //TODO needs to be some kind of guaruntee that this image exists on the local client, if not, it needs to be accessed
    <T extends TickContainer> T startupContainer(CreationTerms<T> terms) throws InterruptedException;

}
