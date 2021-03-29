package me.aurium.tick.docker.image;

import com.github.dockerjava.api.DockerClient;

public interface PullStrategyProvider<T extends PullStrategy> {

    T getStrategy(DockerClient client);

}
