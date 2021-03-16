package me.aurium.tick.rapid;

import org.testcontainers.containers.JdbcDatabaseContainer;

import java.util.Optional;

public class DBSingleton {

    private static DBSingleton instance;

    private JdbcDatabaseContainer<?> container;

    public void setContainer(JdbcDatabaseContainer<?> container) {
        this.container = container;
    }

    public Optional<JdbcDatabaseContainer<?>> getContainer() {
        return Optional.ofNullable(container);
    }

    public static DBSingleton get() {
        if (instance == null) {
            return instance = new DBSingleton();
        } else {
            return instance;
        }
    }
}
