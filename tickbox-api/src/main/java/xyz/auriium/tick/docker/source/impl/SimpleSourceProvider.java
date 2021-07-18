package xyz.auriium.tick.docker.source.impl;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.LocalDirectorySSLConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
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

        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(pair.getHost())
                .withDockerTlsVerify(options.isWithTLS())
                .withCustomSslConfig(new LocalDirectorySSLConfig(Paths.get(System.getProperty("user.home") + "/.docker/machine/certs/").toString())) //fucking weird ass shit,
                // theres 2 sslconfigs and for some reason this only takes the deprecated version. Please advise.
                .build();

        DockerHttpClient client = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();

        return new DockerSourceImpl(pair, DockerClientImpl.getInstance(config,client));
    }

    public abstract URI makeURI(CreationOptions options);

}
