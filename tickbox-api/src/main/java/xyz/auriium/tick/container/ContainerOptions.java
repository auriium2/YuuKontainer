package xyz.auriium.tick.container;

/**
 * Options that pertain to the creation of containers
 */
public class ContainerOptions {

    private final int containerShutdownWait;

    public ContainerOptions(int containerShutdownWait) {
        this.containerShutdownWait = containerShutdownWait;
    }

    public int getContainerShutdownWait() {
        return containerShutdownWait;
    }

    public static ContainerOptions defaults() {
        return new ContainerOptions(500);
    }
}
