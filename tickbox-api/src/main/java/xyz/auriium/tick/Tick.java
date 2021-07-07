package xyz.auriium.tick;

import xyz.auriium.tick.container.ContainerManager;

/**
 * Represents a prestarted Tick that can provide multiple containers
 */
public interface Tick {

    ContainerManager getManager();

}
