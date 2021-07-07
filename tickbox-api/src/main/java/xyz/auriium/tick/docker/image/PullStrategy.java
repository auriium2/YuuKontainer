package xyz.auriium.tick.docker.image;

public interface PullStrategy {

    boolean shouldLoad(String dockerImageName);
    void loadBlocking(String dockerImageName) throws InterruptedException; //TODO not blocking (This would be a hell of a lot
    // easier if the docker-java api simply used futures and not this convoluted callback system)

}
