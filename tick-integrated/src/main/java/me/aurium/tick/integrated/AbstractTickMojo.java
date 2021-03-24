package me.aurium.tick.integrated;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractTickMojo extends AbstractMojo {

    @Parameter(defaultValue = "MARIA_DB")
    protected CommonInits initializer;

    @Parameter(required = true)
    protected String[] locations;

    @Parameter(defaultValue = "target/generated-sources")
    protected String outputDirectory;

    @Parameter(defaultValue = "me.aurium.tick.sources")
    protected String packageName;

    @Parameter(defaultValue = "default")
    protected String defaultDockerName;

    @Parameter(defaultValue = "sandbox")
    protected String dbName;

    @Parameter(defaultValue = "sandboxPassword")
    protected String dbPassword;

    @Parameter(defaultValue = "sandboxUsername")
    protected String dbUsername;

    @Parameter(defaultValue = "50000")
    protected int dbPort;

}
