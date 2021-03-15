package me.aurium.tick;

import org.apache.maven.plugins.annotations.Parameter;

public class ContainerContext {

    public String getImageName() {
        return imageName;
    }

    public Integer getOpenPort() {
        return openPort;
    }

    @Parameter
    private String imageName;

    @Parameter
    private Integer openPort;

}
