package xyz.auriium.tick.docker.source;

import xyz.auriium.tick.docker.source.SourceProvideException;

public class ShellExecutionException extends SourceProvideException {

    public ShellExecutionException(Throwable throwable) {
        super(throwable);
    }

    public ShellExecutionException(String string) {
        super(string);
    }

}
