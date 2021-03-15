package me.aurium.tick.easy;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTickMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(defaultValue = "tick.port")
    private String tickPortProperty;

    @Parameter(defaultValue = "tick.host")
    private String tickHostProperty;

    @Parameter //port for docker to use: THIS IS NOT THE PORT USED BY
    private int dockerPort = 50000;

    @Parameter
    private Map<String,String> environmentVariables = new HashMap<>();

    protected Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }

    protected void setTickPortProperty(int port) {
        setProperty(tickPortProperty,port);
    }

    protected void setTickHostProperty(String string) {
        setProperty(tickHostProperty,string);
    }

    protected int getDockerPort() {
        return dockerPort;
    }

    private void setProperty( String property, Object value ) {
        assert property != null;
        assert value != null;

        project.getProperties().put( property, value.toString() );
    }

}
