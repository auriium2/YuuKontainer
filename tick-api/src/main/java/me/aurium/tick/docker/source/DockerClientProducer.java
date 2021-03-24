package me.aurium.tick.docker.source;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DockerClientProducer {

    private final DockerLocation source;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DockerClientProducer(DockerLocation source) {
        this.source = source;
    }

    public DockerClient produce(ClientOptions options) {

        logger.info("(TICK) Url is: " + source.getUrl());

        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(source.getUrl())
                .withDockerTlsVerify(options.isWithTLS())
                .build();

        DockerHttpClient client = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();

        //initialize!

        return DockerClientImpl.getInstance(config,client); //now we have le docker
    }

}
