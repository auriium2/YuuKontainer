package xyz.auriium.tick.docker.adapter;

import java.util.concurrent.CompletableFuture;

public interface FutureAdapter<T> {

    CompletableFuture<T> toFuture();

}
