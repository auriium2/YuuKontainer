package xyz.auriium.kontainer.container;

import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.PortBinding;
import xyz.auriium.kontainer.centralized.ResourceManager;
import xyz.auriium.kontainer.docker.source.DockerSource;

import java.util.Optional;

public class TinyImageTerms implements CreationTerms<TinyContainer>{

    private final String name;
    private final String image;

    public TinyImageTerms(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public TinyImageTerms(String name) {
        this.name = name;
        this.image = "redis:latest";
    }

    @Override
    public String getDockerImageName() {
        return image;
    }

    @Override
    public String[] getParameters() {
        return new String[0];
    }

    @Override
    public Optional<Bind[]> getBinds() {
        return Optional.empty();
    }

    @Override
    public Optional<PortBinding[]> getPortBindings() {
        return Optional.empty();
    }

    @Override
    public Optional<String[]> getCommands() {
        return Optional.empty();
    }

    @Override
    public String getContainerName() {
        return name;
    }

    @Override
    public TinyContainer instantiateHolder(DockerSource location, ResourceManager manager, String dockerID) {
        return new TinyContainer(manager, name, dockerID);
    }
}
