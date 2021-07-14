package xyz.auriium.tick.container.container;

import java.util.Set;

public interface ContainerStateManager {

    Set<Integer> getAllContainerIDs();

    void stop();

}
