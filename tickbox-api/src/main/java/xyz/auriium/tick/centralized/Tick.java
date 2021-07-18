package xyz.auriium.tick.centralized;

import xyz.auriium.tick.container.CreationTerms;
import xyz.auriium.tick.container.TickContainer;
import xyz.auriium.tick.model.Stoppable;

/**
 * Entry point into the tick api, allows for the creation of dockerized containers.
 * Must be stopped manually if you don't use a resource manager
 */
public interface Tick extends Stoppable {

    /**
     * Creates and starts a new container according to the terms provided
     * @param terms the terms of creation used to generate the tick
     * @param <T> the type of container
     * @return a new container
     */
    <T extends TickContainer> T createContainer(CreationTerms<T> terms);

}
