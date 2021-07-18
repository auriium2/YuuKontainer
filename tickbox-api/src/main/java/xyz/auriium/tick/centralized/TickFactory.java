package xyz.auriium.tick.centralized;

/**
 * Represents something that can make Ticks
 */
public interface TickFactory {

    Tick produce();

}
