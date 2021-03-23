package me.aurium.tick.docker;

import me.aurium.tick.docker.source.DockerLocation;

public class DockerLocationImpl implements DockerLocation {

    private final String ip;
    private final String url;

    public DockerLocationImpl(String ip, String url) {
        this.ip = ip;
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public String getUrl() {
        return url;
    }

}
