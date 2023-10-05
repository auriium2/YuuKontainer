package xyz.auriium.kontainer.docker.source.impl;

import xyz.auriium.kontainer.KontainerException;

/**
 * Exception thrown when there is no valid provider for the {@link AutoSourceProvider}
 */
public class NoProviderException extends KontainerException {


    public NoProviderException() {
        super("No valid provider could be found for use with your system!");
    }
}
