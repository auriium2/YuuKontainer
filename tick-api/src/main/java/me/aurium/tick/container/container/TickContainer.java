package me.aurium.tick.container.container;

/**
 * Represents an already created Container that can start and stop itself
 */
public interface TickContainer extends AutoCloseable {

    String managedContainerName();

    String containerID();

    void start();
    void stop();

}
