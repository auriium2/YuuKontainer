package me.aurium.tick.easy.util;

import org.testcontainers.containers.GenericContainer;

import java.util.*;

/**
 * As a248 once said, **hiss**
 */
public class DBSingleton {

    private static DBSingleton instance;

    private GenericContainer<?> containerInstance;

    public void setContainer(GenericContainer<?> container) {
        this.containerInstance = container;
    }

    public Optional<GenericContainer<?>> getContainer() {
        return Optional.of(containerInstance);
    }

    public static DBSingleton get() {

        if (instance == null) {
            return instance = new DBSingleton();
        } else {
            return instance;
        }
    }


}
