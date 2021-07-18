package xyz.auriium.tick.centralized;

import xyz.auriium.tick.docker.source.DockerSource;

public interface ResourceManagerProvider {

    ResourceManager make(DockerSource source);

}
