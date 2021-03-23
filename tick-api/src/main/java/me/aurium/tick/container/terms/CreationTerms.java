package me.aurium.tick.container.terms;

import com.github.dockerjava.api.DockerClient;
import me.aurium.tick.container.ContainerOptions;
import me.aurium.tick.container.container.TickContainer;

public interface CreationTerms<T extends TickContainer> {
    T creation(DockerClient client, ContainerOptions options);
}
