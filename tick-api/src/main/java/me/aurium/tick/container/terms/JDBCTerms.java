package me.aurium.tick.container.terms;

import me.aurium.tick.container.container.JDBCContainer;

public interface JDBCTerms extends CreationTerms<JDBCContainer> {

    int externalPort();

    String rootPassword();
    String databaseUsername();
    String databasePassword();
    String databaseName();

    String containerName();

}
