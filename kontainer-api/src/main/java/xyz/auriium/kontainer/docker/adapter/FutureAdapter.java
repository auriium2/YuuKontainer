package xyz.auriium.kontainer.docker.adapter;

import java.util.concurrent.CompletableFuture;

public interface FutureAdapter<T> {

    CompletableFuture<T> toFuture();

}
