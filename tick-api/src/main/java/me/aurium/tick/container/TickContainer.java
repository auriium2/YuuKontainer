package me.aurium.tick.container;

/**
 * Represents an already created Container that can start and stop itself
 */
public interface TickContainer extends AutoCloseable {

    String containerID();

    void start();
    void stop();

}
