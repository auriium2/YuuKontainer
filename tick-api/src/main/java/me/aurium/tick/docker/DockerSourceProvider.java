package me.aurium.tick.docker;

import java.util.Optional;

public interface DockerSourceProvider {

    Optional<DockerSource> source();

}
