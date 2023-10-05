package xyz.auriium.kontainer.container;

import xyz.auriium.kontainer.centralized.ResourceManager;

public class TinyContainer implements TickContainer{

    private final ResourceManager manager;
    private final String name;
    private final String id;

    public TinyContainer(ResourceManager manager, String name, String id) {
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
