package xyz.auriium.tick.docker.image;

import com.github.dockerjava.api.DockerClient;

public class CachedPullStrategyProvider implements PullStrategyProvider<CachedPullStrategy>{
    @Override
    public CachedPullStrategy getStrategy(DockerClient client) {
        return new CachedPullStrategy(client);
    }
}
