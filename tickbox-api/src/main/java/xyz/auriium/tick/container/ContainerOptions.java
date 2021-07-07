package xyz.auriium.tick.container;

//todo make this more useful
public class ContainerOptions {

    private final int containerShutdownWait;

    public ContainerOptions(int containerShutdownWait) {
        this.containerShutdownWait = containerShutdownWait;
    }

    public int getContainerShutdownWait() {
        return containerShutdownWait;
    }
}
