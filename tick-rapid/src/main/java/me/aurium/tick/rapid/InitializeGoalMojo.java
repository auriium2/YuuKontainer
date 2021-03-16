package me.aurium.tick.rapid;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.testcontainers.containers.JdbcDatabaseContainer;

@Mojo(name = "initialize", defaultPhase = LifecyclePhase.INITIALIZE)
public class InitializeGoalMojo extends AbstractTickMojo{

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        JdbcDatabaseContainer<?> container = initializer.initializeContainer(sandboxUser,sandboxPassword,sandboxName);

        getLog().info("(TICK) Starting container!");

        container.start();

        getLog().info("(TICK) Setting external access port: " + container.getFirstMappedPort());

        setParameter(externalDockerPort,container.getFirstMappedPort().toString());

        getLog().info("(TICK) Setting internal access ports: " + container.getExposedPorts());

        //TODO

        getLog().info("(TICK) Setting external docker ip: " + container.getContainerIpAddress());

        setParameter(externalDockerAddress,container.getContainerIpAddress());

        getLog().info("(TICK) Host: " + container.getHost());

        getLog().info("(TICK) Setting internal JDBC Url: " + container.getJdbcUrl());

        setParameter(internalJDBCUrl,container.getJdbcUrl());

        getLog().info("(TICK) Setting access");

        singleton.setContainer(container);
    }
}
