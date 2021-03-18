package me.aurium.tick.rapid;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.utility.ResourceReaper;

import java.util.Optional;

@Mojo(name = "teardown", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class TeardownGoalMojo extends AbstractTickMojo{

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("(TICK) Attempting to retrieve container!");

        Optional<JdbcDatabaseContainer<?>> optional = singleton.getContainer();

        if (optional.isPresent()) {
            getLog().info("(TICK) Retrieved optional, attempting teardown!");

            ResourceReaper.instance().stopAndRemoveContainer(optional.get().getContainerId());

            getLog().info("(TICK) Teardown successful!");
        } else {
            throw new MojoFailureException("No container found to teardown!");
        }

    }
}
