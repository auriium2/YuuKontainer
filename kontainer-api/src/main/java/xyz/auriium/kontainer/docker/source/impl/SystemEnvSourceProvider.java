package xyz.auriium.kontainer.docker.source.impl;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import xyz.auriium.kontainer.container.CreationOptions;
import xyz.auriium.kontainer.docker.source.*;

import java.net.URI;

/**
 * Provider that attempts to use system variables in order to provide a docker source
 *
 * Analogous to TestContainer's EnvironmentAndSystemPropertyClientProviderStrategy (geez)
 */
public class SystemEnvSourceProvider extends SimpleSourceProvider {

    @Override
    public String name() {
        return "SystemEnvSourceProvider";
    }

    @Override
    public Integer priority() {
        return 50;
    }

    @Override
    public ApplicableResult isApplicable() {
        return System.getenv("DOCKER_HOST") != null ? ApplicableResult.success() : ApplicableResult.fail("System Environment Variables for docker could not be located or are null! Please fill them out!");
    }

    @Override
    public URI makeURI(CreationOptions options) {
        return DefaultDockerClientConfig.createDefaultConfigBuilder().build().getDockerHost();
    }
}
