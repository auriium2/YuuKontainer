package me.aurium.tick.docker;

import com.github.dockerjava.transport.SSLConfig;
import me.aurium.tick.docker.source.DockerLocation;

public class DockerLocationImpl implements DockerLocation {

    private final String ip;
    private final String url;
    private final SSLConfig config;

    public DockerLocationImpl(String ip, String url, SSLConfig config) {
        this.ip = ip;
        this.url = url;
        this.config = config;
    }

    public String getIp() {
        return ip;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public SSLConfig getSSLConfig() {
        return config;
    }

}
