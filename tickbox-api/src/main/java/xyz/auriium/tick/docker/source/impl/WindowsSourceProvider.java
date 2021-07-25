package xyz.auriium.tick.docker.source.impl;

import org.apache.commons.lang3.SystemUtils;
import xyz.auriium.tick.container.CreationOptions;
import xyz.auriium.tick.docker.source.ApplicableResult;

import java.net.URI;

/**
 * Windows only Source Provider that uses npipe socket to provide a docker source handle
 *
 * Literally copy and pasted from TestContainers
 */

public class WindowsSourceProvider extends SimpleSourceProvider {

    protected static final String DOCKER_SOCK_PATH = "//./pipe/docker_engine";
    private static final String SOCKET_LOCATION = "npipe://" + DOCKER_SOCK_PATH;

    @Override
    public String name() {
        return "WindowsSourceProvider";
    }

    @Override
    public Integer priority() {
        return 20;
    }

    @Override
    public ApplicableResult isApplicable() {
        return SystemUtils.IS_OS_WINDOWS ? ApplicableResult.success() : ApplicableResult.fail("OS must be windows to use NPIPE!");
    }

    @Override
    public URI makeURI(CreationOptions options) {
        return URI.create(SOCKET_LOCATION);
    }
}
