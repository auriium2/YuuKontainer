package xyz.auriium.tick;

import xyz.auriium.tick.container.container.TickContainer;
import xyz.auriium.tick.container.terms.CreationTerms;
import xyz.auriium.tick.docker.source.DockerSource;

import java.io.Closeable;

/**
 * Represents a prestarted Tick that can provide multiple containers
 */
public interface Tick extends Closeable {

    /**
     * Gets the relevant source being used by this tick system
     * @return the source
     */
    DockerSource getSource();

    /**
     * Starts a container used
     * @param terms
     * @param <T>
     * @return
     * @throws InterruptedException
     */
    <T extends TickContainer> T startupContainer(CreationTerms<T> terms) throws InterruptedException;

}
