package me.aurium.tick.full;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractTickMojo extends AbstractMojo {
    //encapsulation over inheritance... bah, i don't care, it's a shitty maven plugin (fix this later)

    public CommonInitializers getInitializer() {
        return initializer;
    }

    public String[] getLocations() {
        return locations;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public String getPackageName() {
        return packageName;
    }

    @Parameter(defaultValue = "MARIADB")
    private CommonInitializers initializer;

    @Parameter(required = true)
    private String[] locations;

    @Parameter(defaultValue = "target/generated-sources")
    private String outputDirectory;

    @Parameter(defaultValue = "me.aurium.tick.sources")
    private String packageName;



}
