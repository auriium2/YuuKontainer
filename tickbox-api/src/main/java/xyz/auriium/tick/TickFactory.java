package xyz.auriium.tick;

import xyz.auriium.tick.docker.source.SourceProvideException;

/**
 * Represents something that can make Ticks
 */
public interface TickFactory {

    Tick produce();

}
