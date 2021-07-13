package xyz.auriium.tick;

import xyz.auriium.tick.container.container.TickContainer;
import xyz.auriium.tick.container.terms.CreationTerms;

/**
 * Represents a prestarted Tick that can provide multiple containers
 */
public interface Tick {

    <T extends TickContainer> T startupContainer(CreationTerms<T> terms) throws InterruptedException;

}
