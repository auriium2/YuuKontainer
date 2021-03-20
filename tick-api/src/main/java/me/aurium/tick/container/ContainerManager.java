package me.aurium.tick.container;

public interface ContainerManager extends AutoCloseable {

    //TODO needs to be some kind of guaruntee that this image exists on the local client, if not, it needs to be accessed
    TickContainer produceContainer(CreationTerms terms);

}
