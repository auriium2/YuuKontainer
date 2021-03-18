package me.aurium.tick;

import com.amihaiemil.docker.Docker;

public interface DockerSource {

    Docker produceDocker();

}
