package me.aurium.tick.docker.source;

import com.github.dockerjava.transport.SSLConfig;

public interface DockerSource {

    String getSourceURL();
    String getSourceIP();

    SSLConfig getSSLConfig();

}
