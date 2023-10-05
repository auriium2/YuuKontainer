package xyz.auriium.kontainer.centralized;

import xyz.auriium.kontainer.KontainerException;

public class ShutdownException extends KontainerException {
    public ShutdownException(String s) {
        super(s);
    }
}
