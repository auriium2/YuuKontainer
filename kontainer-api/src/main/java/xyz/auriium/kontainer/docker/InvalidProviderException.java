package xyz.auriium.kontainer.docker;

import xyz.auriium.kontainer.KontainerException;

public class InvalidProviderException extends KontainerException {

    public InvalidProviderException() {
        super("A provider that was provided was unable to meet it's requirements for launch! See logs for details.");
    }

}
