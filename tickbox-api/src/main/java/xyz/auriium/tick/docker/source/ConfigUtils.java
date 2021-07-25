package xyz.auriium.tick.docker.source;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;

//TODO de-testcontainers this

/*
public class ConfigUtils {

    public static final boolean IN_A_CONTAINER = new File("/.dockerenv").exists();

    private static final Optional<String> defaultGateway = Optional
            .ofNullable(DockerClientFactory.instance().runInsideDocker(
                    cmd -> cmd.withCmd("sh", "-c", "ip route|awk '/default/ { print $3 }'"),
                    (client, id) -> {
                        try {
                            LogToStringContainerCallback loggingCallback = new LogToStringContainerCallback();
                            client.logContainerCmd(id).withStdOut(true)
                                    .withFollowStream(true)
                                    .exec(loggingCallback)
                                    .awaitStarted();
                            loggingCallback.awaitCompletion(3, SECONDS);
                            return loggingCallback.toString();
                        } catch (Exception e) {
                            log.warn("Can't parse the default gateway IP", e);
                            return null;
                        }
                    }
            ))
            .map(StringUtils::trimToEmpty)
            .filter(StringUtils::isNotBlank);
    private static final Logger log = LoggerFactory.getLogger("(TICK | Experimental)");

    */
/**
     * @deprecated use {@link DockerClientProviderStrategy#getDockerHostIpAddress()}
     *//*

    @Deprecated
    public static String getDockerHostIpAddress(URI dockerHost) {
        switch (dockerHost.getScheme()) {
            case "http":
            case "https":
            case "tcp":
                return dockerHost.getHost();
            case "unix":
            case "npipe":
                if (IN_A_CONTAINER) {
                    return getDefaultGateway().orElse("localhost");
                }
                return "localhost";
            default:
                return null;
        }
    }

    public static Optional<String> getDefaultGateway() {
        return DockerClientConfigUtils.defaultGateway;
    }

}
*/
