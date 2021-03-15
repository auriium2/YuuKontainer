package me.aurium.tick.full;

import org.testcontainers.containers.JdbcDatabaseContainer;

public interface JDBCInitializer {

    JdbcDatabaseContainer<?> initializeContainer(String username, String password, String databaseName);

    String correspondingJooqClassName();


}
