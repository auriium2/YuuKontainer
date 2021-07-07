package xyz.auriium.tick.container.terms.mariadb;

public class JDBCConfig {

    private final String rootPassword;
    private final String databaseUsername;
    private final String databasePassword;
    private final String databaseName;
    private final String containerName;

    private final int portBinding;

    public JDBCConfig(String rootPassword, String databaseUsername, String databasePassword, String databaseName, String containerName, int portBinding) {
        this.rootPassword = rootPassword;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.databaseName = databaseName;
        this.containerName = containerName;
        this.portBinding = portBinding;
    }

    public String getRootPassword() {
        return rootPassword;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getContainerName() {
        return containerName;
    }

    public int getPortBinding() {
        return portBinding;
    }

}
