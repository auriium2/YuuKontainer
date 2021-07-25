package xyz.auriium.tick.startup;

import com.github.dockerjava.api.exception.DockerClientException;
import org.apache.http.client.ClientProtocolException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.auriium.tick.PostCreationTestException;
import xyz.auriium.tick.centralized.CommonTickFactory;
import xyz.auriium.tick.centralized.HookResourceManager;
import xyz.auriium.tick.docker.image.DefaultPullStrategy;
import xyz.auriium.tick.startup.BadSourceProvider;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BadStartupTest {

    @Test
    public void shouldThrowWhenBadSourceProvided() {
        assertThrows(DockerClientException.class, () -> {
            new CommonTickFactory(new HookResourceManager.Provider(false), new BadSourceProvider(), new DefaultPullStrategy.Provider()).produce();
        });

    }

}
