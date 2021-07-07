package xyz.auriium.tick.docker.image;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.NotFoundException;

import java.util.concurrent.TimeUnit;

public class CachedPullStrategy implements PullStrategy{
    //This is blocking. I don't want it to be blocking, but docker-java-api's callbacks are gross and i'd rather work
    //with futures. Adapting their callbacks to futures will be ugly and shitty and i don't want to.

    private final DockerClient client;

    public CachedPullStrategy(DockerClient client) {
        this.client = client;
    }

    @Override
    public boolean shouldLoad(String dockerImageName) {
        //any suggestions on how to improve this logic?
        try {
            client.inspectImageCmd(dockerImageName).exec();

            return true;
        } catch (NotFoundException exception) {
            return false;
        }
    }

    @Override
    public void loadBlocking(String dockerImageName) throws InterruptedException {
        client.pullImageCmd(dockerImageName).exec(new LoggingPullResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS); //blocking
    }
}
