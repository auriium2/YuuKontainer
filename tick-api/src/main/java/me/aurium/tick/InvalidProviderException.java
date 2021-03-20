package me.aurium.tick;

public class InvalidProviderException extends RuntimeException {

    InvalidProviderException() {
        super("A provider that was provided was unable to meet it's requirements for launch! See logs for details.");
    }

}
