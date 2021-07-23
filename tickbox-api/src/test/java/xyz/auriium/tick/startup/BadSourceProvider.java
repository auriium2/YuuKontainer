package xyz.auriium.tick.startup;

import xyz.auriium.tick.container.CreationOptions;
import xyz.auriium.tick.docker.source.ApplicableResult;
import xyz.auriium.tick.docker.source.DockerSourceProvider;
import xyz.auriium.tick.docker.source.impl.SimpleSourceProvider;

import java.net.URI;

/**
 * Represents a malfunctioning source provider that cannot tell if it is accurately providing valid fields
 */
public class BadSourceProvider extends SimpleSourceProvider {
    @Override
    public String name() {
        return "BadSourceProvider";
    }

    @Override
    public Integer priority() {
        return 1;
    }

    @Override
    public ApplicableResult isApplicable() {
        return ApplicableResult.success(); //always true to simulate fallacy
    }

    @Override
    public URI makeURI(CreationOptions options) {
        return URI.create("https://i.shit.my.pants.com");
    }
}
