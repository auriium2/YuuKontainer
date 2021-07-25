package xyz.auriium.tick.container;

import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.PortBinding;
import xyz.auriium.tick.centralized.ResourceManager;
import xyz.auriium.tick.docker.source.DockerSource;
import xyz.auriium.tick.utils.Optionals;

import java.util.Optional;

public class TinyImageTerms implements CreationTerms<TinyContainer>{

    private final String name;

    public TinyImageTerms(String name) {
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
    public Optional<Bind[]> getBinds() {
        return Optional.empty();
    }

    @Override
    public Optional<PortBinding[]> getPortBindings() {
        return Optional.empty();
    }

    @Override
    public Optional<String[]> getCommands() {
        return Optionals.supply("top");
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
