package me.aurium.tick;

import me.aurium.tick.container.ContainerManager;

public class CommonTick implements Tick{

    private final ContainerManager manager;

    public CommonTick(ContainerManager manager) {
        this.manager = manager;
    }

    @Override
    public void close() throws Exception {
        manager.close();
    }

    @Override
    public ContainerManager getManager() {
        return manager;
    }
}