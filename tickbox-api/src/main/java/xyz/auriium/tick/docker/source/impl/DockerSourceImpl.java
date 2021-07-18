package xyz.auriium.tick.docker.source.impl;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Network;
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
        switch (sourceURI.getScheme()) {
            case "http":
            case "https":
            case "tcp":
                return sourceURI.getHost();
            case "unix":
            case "npipe":
                /*if (DockerClientConfigUtils.IN_A_CONTAINER) { //TODO de-testcontainers this
                    return client.inspectNetworkCmd()
                            .withNetworkId("bridge")
                            .exec()
                            .getIpam()
                            .getConfig()
                            .stream()
                            .filter(it -> it.getGateway() != null)
                            .findAny()
                            .map(Network.Ipam.Config::getGateway)
                            .orElseGet(() -> {
                                return DockerClientConfigUtils.getDefaultGateway().orElse("localhost");
                            });
                }*/
                return "localhost";
            default:
                throw new IllegalStateException("Unusual scheme used: Cannot interpret scheme: " + sourceURI.getScheme());
        }

    }

    @Override
    public DockerClient getClient() {
        return client;
    }


}
