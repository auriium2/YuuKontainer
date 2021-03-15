package me.aurium.tick.full;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractTickMojo extends AbstractMojo {
    //encapsulation over inheritance... bah, i don't care, it's a shitty maven plugin (fix this later)

    @Parameter(defaultValue = "MARIADB")
    protected CommonInitializers initializer;

    @Parameter(required = true)
    protected String[] locations;

    @Parameter(defaultValue = "target/generated-sources")
    protected String outputDirectory;

    @Parameter(defaultValue = "me.aurium.tick.sources")
    protected String packageName;



}
