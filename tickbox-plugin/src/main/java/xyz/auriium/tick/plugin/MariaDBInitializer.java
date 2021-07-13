package xyz.auriium.tick.plugin;

import xyz.auriium.tick.container.terms.JDBCTerms;
import xyz.auriium.tick.container.terms.mariadb.MariaDBTerms;

public class MariaDBInitializer implements Initializer{
    @Override
    public JDBCTerms getTerms(String dbname, String username, String password, int exposedPort) {
        return new MariaDBTerms("ignored",username,password,dbname,dbname,exposedPort);
    }
}
