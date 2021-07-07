package xyz.auriium.tick.docker.source;

public class SourceProvideException extends Exception {

    public SourceProvideException(String reason) {
        super(reason);
    }

    public SourceProvideException(Throwable throwable) {
        super(throwable);
    }

}
