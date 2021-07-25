package xyz.auriium.tick.utils;

import java.util.Optional;

/**
 * lazy class for lazy developer
 *
 * please everything else is held to a higher standard than TestContainers just lemme get away with this one
 */
public class Optionals {


    /**
     * I hate my life
     * @param vars unsafe varargs
     * @param <T> unsafe
     * @return un safe
     * i wish arrays[]{"weren't so annoying to create"}
     */
    @SafeVarargs
    public static <T> Optional<T[]> supply(T... vars) {
        return Optional.of(vars);
    }
}
