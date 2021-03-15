package me.aurium.tick;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.List;
import java.util.Map;

public abstract class AbstractContainerMojo extends AbstractMojo {

    @Parameter
    private String imageName;

    @Parameter //port for docker to use: THIS IS NOT THE PORT USED BY
    private int dockerPort = 50000;

    @Parameter(defaultValue = "tick.port")
    private String tickPortProperty;

    @Parameter(defaultValue = "tick.host")
    private String tickHostProperty;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    protected Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }

    @Parameter
    private Map<String,String> environmentVariables;

    protected void setTickPortProperty(int port) {
        setProperty(tickPortProperty,port);
    }

    protected void setTickHostProperty(String string) {
        setProperty(tickHostProperty,string);
    }

    protected String getImageName() {
        return imageName;
    }

    protected int getDockerPort() {
        return dockerPort;
    }

    protected String getTickHostProperty() {
        return tickHostProperty;
    }

    protected String getTickPortProperty() {
        return tickPortProperty;
    }

    private void setProperty( String property, Object value ) {
        assert property != null;
        assert value != null;

        project.getProperties().put( property, value );
    }


}
