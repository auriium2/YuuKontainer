package me.aurium.tick;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

public abstract class AbstractContainerMojo extends AbstractMojo {

    protected int getPort() {
        return port;
    }

    protected String getDatabaseName() {
        return databaseName;
    }

    protected List<ContainerContext> getContainerContexts() {
        return containerContexts;
    }

    @Parameter
    private int port = 50000;

    @Parameter(defaultValue = "sandbox")
    private String databaseName;

    @Parameter
    private List<ContainerContext> containerContexts;

}
