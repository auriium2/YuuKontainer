package me.aurium.tick.easy;

import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractJDBCStartupMojo extends AbstractStartupMojo {

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    @Parameter(defaultValue = "sandbox_user")
    private String databaseUsername;

    @Parameter(defaultValue = "sandbox_pass")
    private String databasePassword;

    @Parameter(defaultValue = "sandbox_name")
    private String databaseName;

}
