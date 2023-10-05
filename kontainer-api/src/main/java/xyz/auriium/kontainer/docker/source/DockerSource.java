package xyz.auriium.kontainer.docker.source;

import com.github.dockerjava.api.DockerClient;

import java.net.URI;

/**
 * Wrapper interface for DockerClient
 */
public interface DockerSource {

    /**
     * Gets the full connection URI (Think https://127.0.0.1/)
     *
     * This is NOT a mysql/postgresql/whatever the fuck you want/jdbc connection uri. Those are created elsewhere.
     *
     * @return the URI of this source
     */
    URI getSourceURI();

    /**
     * Get just the host address of this source (Think 127.0.0.1)
     *
     * Typically what gets plugged into
     *
     * @return the host address of this source.
     */
    String getSourceHost();

    /**
     * Get the client.
     * @return cluwne
     */
    DockerClient getClient();

}
