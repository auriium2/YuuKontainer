package me.aurium.tick.docker.source;

import java.util.Optional;

public interface DockerSourceProvider {

    Optional<DockerSource> source();

}
