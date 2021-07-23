package xyz.auriium.tick.centralized;

import xyz.auriium.tick.docker.source.DockerSource;

public class EmptyResourceManager implements ResourceManager{

    EmptyResourceManager() {}

    @Override
    public void submitContainer(String id, boolean val) {

    }

    @Override
    public void destroyContainer(String id) {

    }

    @Override
    public void submitImage(String imageName) {

    }

    @Override
    public void destroyImage(String imageName) {

    }

    @Override
    public void stop() {

    }

    public static class Provider implements ResourceManagerProvider {

        @Override
        public ResourceManager make(DockerSource source) {
            return new EmptyResourceManager();
        }
    }
}
