package me.aurium.tick;

import me.aurium.tick.util.DBSingleton;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Mojo(name = "initialize-containers", defaultPhase = LifecyclePhase.INITIALIZE, requiresDependencyResolution = ResolutionScope.TEST)
public class ContainerInitializeMojo extends AbstractContainerMojo {

    private final DBSingleton singleton = DBSingleton.get();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().debug("(Tick) Loading all contexts and iterating now!");

        for (ContainerContext containerContext : this.getContainerContexts()) {
            getLog().debug("(Tick) Initializing new container with docker image type: " + containerContext.getImageName());

            GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse(containerContext.getImageName()))
                    .withExposedPorts(containerContext.getOpenPort());

            getLog().debug("(Tick) Attempting to start container!");

            container.start();

            getLog().info("(Tick) Initialized TestContainer with details!" +
                    " ID: " + container.getContainerId() +
                    " Address: " + container.getContainerIpAddress() +
                    " Docker Type: " + container.getDockerImageName() +
                    " Port Bindings: " + container.getPortBindings() +
                    " Exposed Ports: " + container.getExposedPorts());

            container.getHost();
            container.getFirstMappedPort();
            //this might be an issue since testcontainers remaps the ports, meaning that if we want to use flyway with it there will be consequences

            singleton.addContainer(container);
        }
    }
}
