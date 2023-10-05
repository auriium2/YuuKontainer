package xyz.auriium.kontainer.shutdown;

import com.github.dockerjava.api.exception.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import xyz.auriium.kontainer.BaseTest;
import xyz.auriium.kontainer.centralized.CommonTickFactory;
import xyz.auriium.kontainer.centralized.HookResourceManager;
import xyz.auriium.kontainer.centralized.Tick;
import xyz.auriium.kontainer.container.CreationOptions;
import xyz.auriium.kontainer.container.TinyImageTerms;
import xyz.auriium.kontainer.docker.image.DefaultPullStrategy;
import xyz.auriium.kontainer.docker.source.DockerSource;
import xyz.auriium.kontainer.docker.source.impl.WindowsSourceProvider;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledOnOs(OS.WINDOWS)
public class TickClosureTest extends BaseTest {

    private static volatile DockerSource notClosedSource;

    @BeforeAll
    public static void startup() {
        notClosedSource = new WindowsSourceProvider().source(CreationOptions.defaults());
    }

    @AfterAll
    public static void teardown() throws IOException {
        notClosedSource.getClient().close();
    }

    @Test
    public void whenStopped_ContainerShouldNotExist() {
        Tick tick = new CommonTickFactory(new HookResourceManager.Provider(false), new WindowsSourceProvider(), new DefaultPullStrategy.Provider()).produce();

        tick.createContainer(new TinyImageTerms("should-not-exist"));

        tick.stop();

        assertThrows(NotFoundException.class, () -> {
            notClosedSource.getClient().inspectContainerCmd("should-not-exist").exec();
        });
    }

    @Test
    public void whenStopped_ContainerShouldNotAllowExpose() {
        Tick tick = new CommonTickFactory(new HookResourceManager.Provider(false), new WindowsSourceProvider(), new DefaultPullStrategy.Provider()).produce();

        tick.createContainer(new TinyImageTerms("should-not-expose"));

        tick.stop();
    }


}
