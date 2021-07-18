package xyz.auriium.tick.docker.source.impl;

import org.apache.commons.lang.SystemUtils;
import xyz.auriium.tick.container.CreationOptions;
import xyz.auriium.tick.docker.source.ApplicableResult;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Provider that uses unix's socket in order to provide a source handle
 *
 * Ripped from TestContainers
 */
public class UnixSourceProvider extends SimpleSourceProvider {

    protected static final String DOCKER_SOCK_PATH = "/var/run/docker.sock";
    private static final String SOCKET_LOCATION = "unix://" + DOCKER_SOCK_PATH;
    private static final int SOCKET_FILE_MODE_MASK = 0xc000;


    @Override
    public String name() {
        return "UnixSourceProvider";
    }

    @Override
    public Integer priority() {
        return 21;
    }

    @Override
    public ApplicableResult isApplicable() {
        if (!(SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_MAC)) return ApplicableResult.fail("System is not UNIX based!");

        Integer mode;
        try {
            mode = (Integer) Files.getAttribute(Paths.get(DOCKER_SOCK_PATH), "unix:mode");
        } catch (IOException e) {
            return ApplicableResult.fail("Could not find unix domain socket!");
        }

        if ((mode & 0xc000) != SOCKET_FILE_MODE_MASK) {
            return ApplicableResult.fail("Found docker unix domain socket but file mode was not as expected (expected: srwxr-xr-x). This problem is possibly due to occurrence of this issue in the past: https://github.com/docker/docker/issues/13121");
        }

        return ApplicableResult.success();
    }

    @Override
    public URI makeURI(CreationOptions options) {
        return URI.create(SOCKET_LOCATION);
    }
}
