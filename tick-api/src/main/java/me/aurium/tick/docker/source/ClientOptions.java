package me.aurium.tick.docker.source;

//TODO make this more useful
public class ClientOptions {

    private final boolean withTLS;

    public ClientOptions(boolean withTLS) {
        this.withTLS = withTLS;
    }

    public boolean isWithTLS() {
        return withTLS;
    }
}
