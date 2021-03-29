package me.aurium.tick.docker.adapter;

import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.PullResponseItem;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

//todo-switch to async-callback
public class FutureCallbackAdapter extends PullImageResultCallback implements FutureAdapter<PullResponseItem> {

    private CompletableFuture<PullResponseItem> response = new CompletableFuture<>();

    private OrThrowable<PullResponseItem> objectCached;

    @Override
    public void onNext(PullResponseItem item) {
        super.onNext(item);

        this.objectCached.assignObject(item);
    }

    @Override
    public void onComplete() {
        super.onComplete();

        objectCached.complete(response);
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);

        this.objectCached.assignThrowable(throwable); //will this cause thread safety concerns? e.g. onError and onNext vs onComplete?
        //i'm assuming not cause everything will be on the same thread (This will be on another thread that is the same thread
        // the orThrowable is on)
    }

    @Override
    public void onStart(Closeable stream) {
        super.onStart(stream); //what the fuck? please advise
    }

    public CompletableFuture<PullResponseItem> toFuture() {
        return response;
    }
}
