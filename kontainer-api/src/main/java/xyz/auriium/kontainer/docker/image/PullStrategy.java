package xyz.auriium.kontainer.docker.image;

public interface PullStrategy {

    boolean shouldLoad(String dockerImageName);
    void load(String dockerImageName);

    default void loadIfRequired(String name) {
        if (shouldLoad(name)) {
            load(name);
        }
    }

}
