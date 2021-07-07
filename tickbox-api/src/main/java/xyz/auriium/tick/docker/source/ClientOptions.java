package xyz.auriium.tick.docker.source;

//TODO make this more useful
public class ClientOptions {

    private final boolean withTLS;
    private final String preferredDockerMachineName;

    public ClientOptions(boolean withTLS, String preferredDockerMachineName) {
        this.withTLS = withTLS;
        this.preferredDockerMachineName = preferredDockerMachineName;
    }

    public boolean isWithTLS() {
        return withTLS;
    }

    public String getPreferredDockerMachineName() {
        return preferredDockerMachineName;
    }
}
