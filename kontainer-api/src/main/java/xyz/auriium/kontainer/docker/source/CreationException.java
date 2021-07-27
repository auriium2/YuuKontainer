package xyz.auriium.kontainer.docker.source;

public class CreationException extends RuntimeException{

    public CreationException(String message) {
        super(message);
    }

    public CreationException(Throwable cause) {
        super(cause);
    }
}
