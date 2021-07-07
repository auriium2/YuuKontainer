package xyz.auriium.tick.docker.source.impl;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import xyz.auriium.tick.docker.source.*;

public class SystemEnvSourceProvider implements DockerSourceProvider {
    @Override
    public String name() {
        return "SystemEnvSourceProvider";
    }

    @Override
    public Integer priority() {
        return 50;
    }

    @Override
    public DockerSource source(ClientOptions options) throws SourceProvideException {

        DefaultDockerClientConfig.createDefaultConfigBuilder();

        return null;
    }

    @Override
    public ApplicableResult isApplicable() {
        return null;
    }


}
