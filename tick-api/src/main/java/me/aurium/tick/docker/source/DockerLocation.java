package me.aurium.tick.docker.source;

import com.github.dockerjava.transport.SSLConfig;

public interface DockerLocation {

    String getIp();
    String getUrl();
    SSLConfig getSSLConfig();


}
