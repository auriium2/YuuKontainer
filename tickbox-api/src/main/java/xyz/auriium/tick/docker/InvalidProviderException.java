package xyz.auriium.tick.docker;

public class InvalidProviderException extends RuntimeException {

    public InvalidProviderException() {
        super("A provider that was provided was unable to meet it's requirements for launch! See logs for details.");
    }

}
