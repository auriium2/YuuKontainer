package xyz.auriium.tick.docker.source.impl;

import xyz.auriium.tick.container.CreationOptions;
import xyz.auriium.tick.docker.source.*;

import java.net.URI;
import java.util.function.Supplier;

/**
 * Manual provider that provides a source using a given URI.
 */
public class ManualSourceProvider extends SimpleSourceProvider {

    private final URI hostURI;
    private final Supplier<ApplicableResult> success;

    /**
     * A manual provider that provides a source using given URI.
     * @param hostURI the URI to use when creating the source handle
     * @param success a lambda function that determines whether the URI is usable or not.
     */
    public ManualSourceProvider(URI hostURI, Supplier<ApplicableResult> success) {
        this.hostURI = hostURI;
        this.success = success;
    }

    /**
     * Default constructor for provider
     * @param hostURI the URI to use when creating the source handle
     */
    public ManualSourceProvider(URI hostURI) {
        this.hostURI = hostURI;
        this.success = ApplicableResult::success;
    }

    @Override
    public String name() {
        return "ManualSourceProvider";
    }

    @Override
    public Integer priority() {
        return -100000;
    }

    @Override
    public URI makeURI(CreationOptions options) {
        return hostURI;
    }

    @Override
    public ApplicableResult isApplicable() {
        return success.get();
    }
}
