package xyz.auriium.kontainer.container;

import com.github.dockerjava.api.model.PortBinding;

import java.util.Objects;

public class Arguments {

    private final String creationName;
    private final String dockerImageName;
    private final String[] parameters;
    private final PortBinding binding;

    public Arguments(String creationName, String dockerImageName, String[] parameters, PortBinding binding) {
        this.creationName = creationName;
        this.dockerImageName = dockerImageName;
        this.parameters = parameters;
        this.binding = binding;
    }

    public String getDockerImageName() {
        return dockerImageName;
    }

    public String[] getParameters() {
        return parameters;
    }

    public PortBinding getBinding() {
        return this.binding;
    }

    public String getContainerName() {
        return this.creationName;
    }

    public static class Builder {

        private String dockerImageName;
        private String[] params;
        private PortBinding biniding;
        private String creationName;

        public Builder withImage(String name) {
             //todo validator

            this.dockerImageName = name;

            return this;
        }

        public Builder withParams(String... params) {
            this.params = params;

            return this;
        }

        public Builder withBinding(PortBinding binding) {
            this.biniding = binding;

            return this;
        }

        public Builder withCreationName(String name) {
            creationName = name;

            return this;
        }

        public Arguments build() {
            Objects.requireNonNull(dockerImageName);
            Objects.requireNonNull(params);
            Objects.requireNonNull(biniding);
            Objects.requireNonNull(creationName);

            return new Arguments(creationName, dockerImageName,params, biniding);
        }


    }

}
