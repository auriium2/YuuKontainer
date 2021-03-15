package me.aurium.tick;

import me.aurium.tick.util.DBSingleton;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.testcontainers.containers.GenericContainer;

@Mojo(name = "teardown-containers", defaultPhase = LifecyclePhase.GENERATE_RESOURCES, requiresDependencyResolution = ResolutionScope.TEST)
public class ContainerTeardownMojo extends AbstractContainerMojo {

    private final DBSingleton singleton = DBSingleton.get();


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        GenericContainer<?> container = singleton.getContainer().orElseThrow(() -> new MojoFailureException("No container exists to stop!"));

        getLog().debug("(Tick) Attempting to stop container with ID: " + container.getContainerId());

        container.stop();

        getLog().info("(Tick) Stopped Container with ID: " + container.getContainerId());

    }
}
