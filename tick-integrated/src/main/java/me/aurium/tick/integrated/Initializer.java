package me.aurium.tick.integrated;

import me.aurium.tick.container.terms.JDBCTerms;

public interface Initializer {

    JDBCTerms getTerms(String dbname, String username, String password, int exposedPort);

}
