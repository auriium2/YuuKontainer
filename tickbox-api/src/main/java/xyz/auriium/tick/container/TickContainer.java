package xyz.auriium.tick.container;

/**
 * Represents an already created Container that can start and stop itself
 *
 * TODO add more methods to this barebones ass cringe bullshit
 *
 * some ideas:
 *
 * runShell(str) for a linux extension of this interface
 * getStatus()
 * get.. idk everything that dockerjava provides for us ;)
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
