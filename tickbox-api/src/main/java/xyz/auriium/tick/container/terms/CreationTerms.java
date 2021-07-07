package xyz.auriium.tick.container.terms;

import com.github.dockerjava.api.DockerClient;
import xyz.auriium.tick.container.ContainerOptions;
import xyz.auriium.tick.container.container.TickContainer;
import xyz.auriium.tick.container.terms.termParts.Arguments;
import xyz.auriium.tick.docker.source.DockerSource;

public interface CreationTerms<T extends TickContainer>  {

    Arguments creationArguments();

    T creation(DockerSource location, DockerClient client, ContainerOptions options, String dockerID);
}
