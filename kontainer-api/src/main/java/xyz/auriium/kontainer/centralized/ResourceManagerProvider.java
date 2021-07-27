package xyz.auriium.kontainer.centralized;

import xyz.auriium.kontainer.docker.source.DockerSource;

public interface ResourceManagerProvider {

    ResourceManager make(DockerSource source);

}
