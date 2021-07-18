package xyz.auriium.tick.docker.image;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface PullStrategy {

    boolean shouldLoad(String dockerImageName);
    void load(String dockerImageName);

    default void loadIfRequired(String name) {
        if (shouldLoad(name)) {
            load(name);
        }
    }

}
