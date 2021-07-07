package xyz.auriium.tick.container;

import xyz.auriium.tick.container.container.TickContainer;
import xyz.auriium.tick.container.terms.CreationTerms;

public interface ContainerManager {

    //TODO needs to be some kind of guaruntee that this image exists on the local client, if not, it needs to be accessed
    <T extends TickContainer> T startupContainer(CreationTerms<T> terms) throws InterruptedException;

}
