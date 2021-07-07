package xyz.auriium.tick;

import java.util.concurrent.CompletableFuture;

public interface ImageManager {

    /**
     * CALLER IS RESPONSIBLE FOR MISSING IMAGE ID EXCEPTIONS.
     *
     * Installs the image id onto the docker-client and tries to give t he image name
     * @param imageName
     * @return
     */
    CompletableFuture<String> loadImageID(String imageName);

}
