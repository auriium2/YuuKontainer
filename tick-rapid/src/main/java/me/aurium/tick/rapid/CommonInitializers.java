package me.aurium.tick.rapid;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MariaDBContainer;

public enum CommonInitializers implements JDBCInitializer {
    MARIADB(new MariaDBContainer<>(),"org.jooq.meta.mariadb.MariaDBDatabase");

    private final JdbcDatabaseContainer<?> consumer;
    private final String jooqClassName;

    CommonInitializers(JdbcDatabaseContainer<?> consumer, String jooqClassName) {
        this.consumer = consumer;
        this.jooqClassName = jooqClassName;
    }

    @Override
    public JdbcDatabaseContainer<?> initializeContainer(String username, String password, String databaseName) {
        return consumer.withDatabaseName(databaseName).withUsername(username).withPassword(password);
    }

    @Override
    public String correspondingJooqClassName() {
        return jooqClassName;
    }
}
