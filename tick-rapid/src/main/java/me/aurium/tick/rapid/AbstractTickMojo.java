package me.aurium.tick.rapid;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

public abstract class AbstractTickMojo extends AbstractMojo {

    protected final DBSingleton singleton = DBSingleton.get();

    @Parameter(defaultValue = "MARIADB")
    protected CommonInitializers initializer;
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    protected MavenProject project;

    @Parameter(defaultValue = "tick.jdbc_port")
    protected String internalJDBCPort;
    @Parameter(defaultValue = "tick.docker_port")
    protected String externalDockerPort;
    @Parameter(defaultValue = "tick.jdbc_url")
    protected String internalJDBCUrl;
    @Parameter(defaultValue = "tick.docker_ip")
    protected String externalDockerAddress;

    @Parameter(defaultValue = "sandbox")
    protected String sandboxName;
    @Parameter(defaultValue = "sandboxUser")
    protected String sandboxPassword;
    @Parameter(defaultValue = "sandboxPassword")
    protected String sandboxUser;

    protected void setParameter(String param, String toSet) {
        assert param != null;
        assert toSet != null;

        project.getProperties().put( param, toSet );
    }

}
