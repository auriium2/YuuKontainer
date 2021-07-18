package xyz.auriium.tick.container;

import xyz.auriium.tick.model.Stoppable;

/**
 * Represents an already created Container that can start and stop itself
 */
public interface TickContainer extends AutoCloseable{

    String containerName();
    String containerID();

    /**
     * Calling this method will stop and then destroy this tick container docker-side.
     * After the container is destroyed do not attempt to invoke it.
     */
    void destroy();

    /**
     * Invokes {@link TickContainer#destroy()}
     */
    default void close() {
        destroy();
    }

}
