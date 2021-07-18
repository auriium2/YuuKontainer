package xyz.auriium.tick.docker.source.impl;

import com.github.dockerjava.core.*;
import com.github.dockerjava.okhttp.OkDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import xyz.auriium.tick.container.CreationOptions;
import xyz.auriium.tick.docker.source.DockerSource;
import xyz.auriium.tick.docker.source.DockerSourceProvider;

import java.net.URI;
import java.nio.file.Paths;

/**
 * Client abstraction to allow source providers to only provide a URI
 */
public abstract class SimpleSourceProvider implements DockerSourceProvider {

    @Override
    public DockerSource source(CreationOptions options) {

        URI pair = makeURI(options);

        DockerHttpClient client = new OkDockerHttpClient.Builder()
                .dockerHost(pair)
                .sslConfig(new LocalDirectorySSLConfig(Paths.get(System.getProperty("user.home") + "/.docker/machine/certs/").toString()))
                .build();

        DefaultDockerClientConfig.Builder configBuilder = DefaultDockerClientConfig.createDefaultConfigBuilder();

        if (configBuilder.build().getApiVersion() == RemoteApiVersion.UNKNOWN_VERSION) {
            configBuilder.withApiVersion(RemoteApiVersion.VERSION_1_30);
        }

        return new DockerSourceImpl(pair, DockerClientImpl.getInstance(configBuilder.withDockerHost(pair.toString()).build(),client));
    }

    public abstract URI makeURI(CreationOptions options);

}
