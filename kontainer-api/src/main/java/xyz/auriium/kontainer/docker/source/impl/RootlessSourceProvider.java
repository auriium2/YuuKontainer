package xyz.auriium.kontainer.docker.source.impl;

import com.sun.jna.Library;
import com.sun.jna.Native;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.kontainer.container.CreationOptions;
import xyz.auriium.kontainer.docker.source.ApplicableResult;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * UNIX only SourceProvider that attempts to run docker regardless of whether it has permission to or not
 *
 * This was copy pasted from testcontainers and adapted to the tickbox format. I have no idea whether
 * or not it actually functions.
 */
public class RootlessSourceProvider extends SimpleSourceProvider{

    private static final Logger logger = LoggerFactory.getLogger("(TICK | ROOTLESS DOCKER PROVIDER)");

    private Path resolveSocketPath() {
        return tryEnv().orElseGet(() -> {
            Path homePath = Paths.get(System.getProperty("user.home")).resolve(".docker").resolve("run");
            return tryFolder(homePath).orElseGet(() -> {
                Path implicitPath = Paths.get("/run/user/" + LibC.INSTANCE.getUUID());
                return tryFolder(implicitPath).orElse(null);
            });
        });
    }

    private Optional<Path> tryEnv() {
        String xdgRuntimeDir = System.getenv("XDG_RUNTIME_DIR");
        if (StringUtils.isBlank(xdgRuntimeDir)) {
            logger.debug("$XDG_RUNTIME_DIR is not set.");
            return Optional.empty();
        }
        Path path = Paths.get(xdgRuntimeDir);
        if (!Files.exists(path)) {
            logger.debug("$XDG_RUNTIME_DIR is set to '{}' but the folder does not exist.", path);
            return Optional.empty();
        }
        Path socketPath = path.resolve("docker.sock");
        if (!Files.exists(socketPath)) {
            logger.debug("$XDG_RUNTIME_DIR is set but '{}' does not exist.", socketPath);
            return Optional.empty();
        }
        return Optional.of(socketPath);
    }

    private Optional<Path> tryFolder(Path path) {
        if (!Files.exists(path)) {
            logger.debug("'{}' does not exist.", path);
            return Optional.empty();
        }
        Path socketPath = path.resolve("docker.sock");
        if (!Files.exists(socketPath)) {
            logger.debug("'{}' does not exist.", socketPath);
            return Optional.empty();
        }
        return Optional.of(socketPath);
    }

    @Override
    public String name() {
        return "RootlessSourceProvider";
    }

    @Override
    public Integer priority() {
        return 29;
    }

    @Override
    public ApplicableResult isApplicable() {
        if (!SystemUtils.IS_OS_LINUX) return ApplicableResult.fail("Must use linux!");
        Path resolve = resolveSocketPath();

        if (resolve == null || !Files.exists(resolve)) return ApplicableResult.fail("Cannot find library used to run rootless!");

        return ApplicableResult.success();
    }

    @Override
    public URI makeURI(CreationOptions options) {
        return URI.create("unix://" + resolveSocketPath().toString());
    }

    private interface LibC extends Library {

        LibC INSTANCE = Native.loadLibrary("c", LibC.class);

        int getUUID();
    }

}
