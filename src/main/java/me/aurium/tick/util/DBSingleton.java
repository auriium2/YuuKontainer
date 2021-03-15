package me.aurium.tick.util;

import org.testcontainers.containers.Container;
import org.testcontainers.containers.GenericContainer;

import java.util.*;

/**
 * As a248 once said, **hiss**
 */
public class DBSingleton {

    private static DBSingleton instance;
    private final List<GenericContainer<?>> containers = new LinkedList<>();

    public Iterable<GenericContainer<?>> getContainers() {
        return containers;
    }

    public void addContainer(GenericContainer<?> container) {
        this.containers.add(container);
    }

    public void wipeContainers() {
        this.containers.clear();
    }

    public static DBSingleton get() {
        return Objects.requireNonNullElseGet(instance, DBSingleton::new); //stupid stupid singleton bs i hate itttttttttttttt
    }


}
