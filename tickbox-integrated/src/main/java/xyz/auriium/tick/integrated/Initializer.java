package xyz.auriium.tick.integrated;

import xyz.auriium.tick.container.terms.JDBCTerms;

public interface Initializer {

    JDBCTerms getTerms(String dbname, String username, String password, int exposedPort);

}
