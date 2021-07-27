package xyz.auriium.kontainer.startup;

import com.github.dockerjava.api.exception.DockerClientException;
import org.junit.jupiter.api.Test;
import xyz.auriium.kontainer.centralized.CommonTickFactory;
import xyz.auriium.kontainer.centralized.HookResourceManager;
import xyz.auriium.kontainer.docker.image.DefaultPullStrategy;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BadStartupTest {

    @Test
    public void shouldThrowWhenBadSourceProvided() {
        assertThrows(DockerClientException.class, () -> {
            new CommonTickFactory(new HookResourceManager.Provider(false), new BadSourceProvider(), new DefaultPullStrategy.Provider()).produce();
        });

    }

}
