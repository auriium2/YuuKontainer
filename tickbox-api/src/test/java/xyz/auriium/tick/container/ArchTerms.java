package xyz.auriium.tick.container;

import com.github.dockerjava.api.model.PortBinding;
import xyz.auriium.tick.centralized.ResourceManager;
import xyz.auriium.tick.docker.source.DockerSource;

public class ArchTerms implements CreationTerms<ArchContainer>{

    private final String name;

    public ArchTerms(String name) {
        this.name = name;
    }

    @Override
    public String getDockerImageName() {
        return "archlinux:latest";
    }

    @Override
    public String[] getParameters() {
        return new String[0];
    }

    @Override
    public PortBinding getBinding() {
        return PortBinding.parse("");
    }

    @Override
    public String getContainerName() {
        return name;
    }

    @Override
    public ArchContainer instantiateHolder(DockerSource location, ResourceManager manager, String dockerID) {
        return new ArchContainer(manager, name, dockerID);
    }
}
