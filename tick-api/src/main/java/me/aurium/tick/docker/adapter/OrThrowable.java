package me.aurium.tick.docker.adapter;

import java.util.concurrent.CompletableFuture;

/**
 * Shitty class that probably will break threadsafety
 * @param <T>
 */
public class OrThrowable<T> {

    private T object;
    private Throwable throwable;

    public void assignObject(T object) {
        this.object = object;
        this.throwable = null;
    }

    public void assignThrowable(Throwable throwable) {
        this.throwable = throwable;
        this.object = null; //YOU CAN ONLY HAVE ONE >:(
    }

    public void complete(CompletableFuture<T> future) {
        if (throwable != null) {
            future.completeExceptionally(throwable);
        } else if (object != null) {
            future.complete(object);
        } else {
            future.completeExceptionally(new NoCompletionResultsException("Tried to complete a future without having an object or a throwable to complete it with!"));
        }
    }

    public static class NoCompletionResultsException extends RuntimeException {
        public NoCompletionResultsException(String aaaaa) {
            super(aaaaa);
        }
    }
}
