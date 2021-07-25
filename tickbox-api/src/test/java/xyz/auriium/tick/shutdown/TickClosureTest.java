package xyz.auriium.tick.shutdown;

import com.github.dockerjava.api.exception.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import xyz.auriium.tick.BaseTest;
import xyz.auriium.tick.centralized.CommonTickFactory;
import xyz.auriium.tick.centralized.HookResourceManager;
import xyz.auriium.tick.centralized.Tick;
import xyz.auriium.tick.container.CreationOptions;
import xyz.auriium.tick.container.TinyImageTerms;
import xyz.auriium.tick.docker.image.DefaultPullStrategy;
import xyz.auriium.tick.docker.source.DockerSource;
import xyz.auriium.tick.docker.source.impl.WindowsSourceProvider;

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
