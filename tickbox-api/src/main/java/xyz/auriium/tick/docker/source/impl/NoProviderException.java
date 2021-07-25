package xyz.auriium.tick.docker.source.impl;

import xyz.auriium.tick.TickException;

/**
 * Exception thrown when there is no valid provider for the {@link AutoSourceProvider}
 */
public class NoProviderException extends TickException {


    public NoProviderException() {
        super("No valid provider could be found for use with your system!");
    }
}
