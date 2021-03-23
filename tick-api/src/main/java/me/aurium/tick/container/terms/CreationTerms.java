package me.aurium.tick.container.terms;

import com.github.dockerjava.api.DockerClient;
import me.aurium.tick.container.ContainerOptions;
import me.aurium.tick.container.container.TickContainer;
import me.aurium.tick.docker.source.DockerLocation;

public interface CreationTerms<T extends TickContainer> {
    T creation(DockerLocation location, DockerClient client, ContainerOptions options);
}
