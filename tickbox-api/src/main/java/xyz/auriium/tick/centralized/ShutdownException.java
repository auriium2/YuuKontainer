package xyz.auriium.tick.centralized;

import xyz.auriium.tick.TickException;

public class ShutdownException extends TickException {
    public ShutdownException(String s) {
        super(s);
    }
}
