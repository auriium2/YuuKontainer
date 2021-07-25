package xyz.auriium.tick.docker;

import xyz.auriium.tick.TickException;

public class InvalidProviderException extends TickException {

    public InvalidProviderException() {
        super("A provider that was provided was unable to meet it's requirements for launch! See logs for details.");
    }

}
