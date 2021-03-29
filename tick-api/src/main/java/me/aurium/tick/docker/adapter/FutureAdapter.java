package me.aurium.tick.docker.adapter;

import java.util.concurrent.CompletableFuture;

public interface FutureAdapter<T> {

    CompletableFuture<T> toFuture();

}
