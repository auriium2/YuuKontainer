package me.aurium.tick.integrated;

import me.aurium.tick.container.terms.JDBCTerms;
import me.aurium.tick.container.terms.MariaDBTerms;

public class MariaDBInitializer implements Initializer{
    @Override
    public JDBCTerms getTerms(String dbname, String username, String password, int exposedPort) {
        return new MariaDBTerms("ignored",username,password,dbname,dbname,exposedPort);
    }
}
