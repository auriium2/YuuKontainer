package me.aurium.tick;

import java.util.concurrent.CompletableFuture;

public interface ImageManager {

    CompletableFuture<String> loadImageID();

}
