package me.aurium.tick;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;

@Mojo(name = "initialize-mariadb", defaultPhase = LifecyclePhase.INITIALIZE)
public class MariaDBMojo extends AbstractStartupMojo{
    @Override
    protected GenericContainer<?> startupContainer() {

        MariaDBContainer<?> container = new MariaDBContainer<>();

        if (getEnvironmentVariables() != null) {
            getEnvironmentVariables().forEach(container::addEnv);
        }

        getLog().info("(Tick) Attempting to start container!");

        container.start();

        getLog().info("(Tick) JDBC URL: " + container.getJdbcUrl());

        return container;
    }
}
