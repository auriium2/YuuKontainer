package xyz.auriium.tick.docker.source.impl;

import xyz.auriium.tick.docker.source.*;

import java.net.URI;

/**
 * Manual provider that provides a source using a given URI. Use this if you
 */
public class ManualSourceProvider implements DockerSourceProvider {

    private final URI hostURI;

    public ManualSourceProvider(URI hostURI) {
        this.hostURI = hostURI;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public Integer priority() {
        return null;
    }

    @Override
    public DockerSource source(CreationOptions options) {
        return null;
    }

    @Override
    public ApplicableResult isApplicable() {
        return null;
    }
}
