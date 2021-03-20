package me.aurium.tick.container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;

public interface CreationTerms {

    CreateContainerResponse creation(DockerClient client);
}
