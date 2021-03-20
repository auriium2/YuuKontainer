package me.aurium.tick;

import me.aurium.tick.container.ContainerManager;

/**
 * Represents a prestarted Tick that can provide multiple containers
 */
public interface Tick extends AutoCloseable {

    ContainerManager getManager();

}
