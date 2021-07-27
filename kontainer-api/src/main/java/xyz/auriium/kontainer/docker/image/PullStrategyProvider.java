package xyz.auriium.kontainer.docker.image;

import com.github.dockerjava.api.DockerClient;
import xyz.auriium.kontainer.centralized.ResourceManager;

/**
 * Provider that exists in order to allow user to specify a strategy without actually initializing one
 * since client initialization is done tick-side
 */
public interface PullStrategyProvider {

    DefaultPullStrategy provide(DockerClient client, ResourceManager manager);

}
