package xyz.auriium.tick;

import xyz.auriium.tick.container.ContainerManager;

public class CommonTick implements Tick{

    private final ContainerManager manager;

    public CommonTick(ContainerManager manager) {
        this.manager = manager;
    }

    @Override
    public ContainerManager getManager() {
        return manager;
    }
}
