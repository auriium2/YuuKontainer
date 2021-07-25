package xyz.auriium.tick.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Simple interface that offers a more definite term than closeable
 * (Stop offers more context to a running service than close)
 *
 * Implements closeable in order to provide autocloseable functionality.
 */
public interface Stoppable extends Closeable {

    /**
     * Stops the service
     */
    void stop();

    /**
     * auto invoke
     */
    @Override
    default void close() throws IOException {
        stop();
    }
}
