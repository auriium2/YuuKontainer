package me.aurium.tick.easy;

import me.aurium.tick.easy.util.DBSingleton;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.testcontainers.containers.GenericContainer;

public abstract class AbstractStartupMojo extends AbstractTickMojo {

    protected abstract GenericContainer<?> startupContainer();

    private final DBSingleton singleton = DBSingleton.get();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        GenericContainer<?> container = startupContainer();

        getLog().info("(Tick) Initialized TestContainer with details!" +
                " ID: " + container.getContainerId() +
                " Address: " + container.getContainerIpAddress() +
                " Docker Type: " + container.getDockerImageName() +
                " Port Bindings: " + container.getPortBindings() +
                " Exposed Ports: " + container.getExposedPorts() +
                " Host: " + container.getHost() +
                " First Mapped Port: " + container.getFirstMappedPort());

        setTickHostProperty(container.getHost());
        setTickPortProperty(container.getFirstMappedPort());

        singleton.setContainer(container);
    }
}
