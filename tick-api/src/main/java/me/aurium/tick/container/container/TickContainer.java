package me.aurium.tick.container.container;

/**
 * Represents an already created Container that can start and stop itself
 *
 * TODO some kind of idempotcy checks to make sure that when a container is closed it's removed
 */
public interface TickContainer extends AutoCloseable {

    String managedContainerName();

    String containerID();

    void start();
    void stop();
    void remove();

}
