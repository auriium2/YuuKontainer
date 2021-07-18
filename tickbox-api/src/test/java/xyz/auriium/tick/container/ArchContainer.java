package xyz.auriium.tick.container;

import xyz.auriium.tick.centralized.ResourceManager;

public class ArchContainer implements TickContainer{

    private final ResourceManager manager;
    private final String name;
    private final String id;

    public ArchContainer(ResourceManager manager, String name, String id) {
        this.manager = manager;
        this.name = name;
        this.id = id;
    }

    @Override
    public String containerName() {
        return name;
    }

    @Override
    public String containerID() {
        return id;
    }

    @Override
    public void destroy() {
        manager.destroyContainer(id);
    }
}
