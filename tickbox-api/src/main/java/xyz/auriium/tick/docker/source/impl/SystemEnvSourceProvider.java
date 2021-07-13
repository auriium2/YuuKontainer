package xyz.auriium.tick.docker.source.impl;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import xyz.auriium.tick.docker.source.*;

/**
 * Provider that attempts to use system variables in order to provide a docker source
 *
 * Analogous to TestContainer's EnvironmentAndSystemPropertyClientProviderStrategy (geez)
 */
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
    public DockerSource source(CreationOptions options) {

        DefaultDockerClientConfig.createDefaultConfigBuilder();

        return null;
    }

    @Override
    public ApplicableResult isApplicable() {
        return System.getenv("DOCKER_HOST") != null ? ApplicableResult.success() : ApplicableResult.fail("System Environment Variables for docker could not be located or are null! Please fill them out!");
    }


}
