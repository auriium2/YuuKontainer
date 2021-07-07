package xyz.auriium.tick.docker.source.impl;

import com.github.dockerjava.api.DockerClient;
import xyz.auriium.tick.docker.source.DockerSource;

import java.net.URI;

public class DockerSourceImpl implements DockerSource {

    private final URI sourceURI;
    private final DockerClient client;

    public DockerSourceImpl(URI sourceURI, DockerClient client) {
        this.sourceURI = sourceURI;
        this.client = client;
    }

    @Override
    public URI getSourceURI() {
        return sourceURI;
    }

    @Override
    public String getSourceHost() {
        return sourceURI.getHost();
    }

    @Override
    public DockerClient getClient() {
        return client;
    }


}
