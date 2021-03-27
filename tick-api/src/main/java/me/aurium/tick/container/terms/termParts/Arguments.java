package me.aurium.tick.container.terms.termParts;

import com.github.dockerjava.api.model.PortBinding;

public interface Arguments {

    String getDockerImageName();

    String[] getParameters();

    PortBinding getBinding();

    String getCreationName();

}
