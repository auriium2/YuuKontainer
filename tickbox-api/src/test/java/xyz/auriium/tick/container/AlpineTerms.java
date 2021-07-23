package xyz.auriium.tick.container;

import com.github.dockerjava.api.model.PortBinding;
import xyz.auriium.tick.centralized.ResourceManager;
import xyz.auriium.tick.docker.source.DockerSource;

import java.util.Optional;

public class AlpineTerms implements CreationTerms<AlpineContainer>{

    private final String name;

    public AlpineTerms(String name) {
        this.name = name;
    }

    @Override
    public String getDockerImageName() {
        return "alpine:latest";
    }

    @Override
    public String[] getParameters() {
        return new String[0];
    }

    @Override
    public Optional<PortBinding> getBinding() {
        return Optional.empty();
    }

    @Override
    public String getContainerName() {
        return name;
    }

    @Override
    public AlpineContainer instantiateHolder(DockerSource location, ResourceManager manager, String dockerID) {
        return new AlpineContainer(manager, name, dockerID);
    }
}
