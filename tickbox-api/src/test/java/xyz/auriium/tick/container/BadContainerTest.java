package xyz.auriium.tick.container;

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
import xyz.auriium.tick.docker.image.DefaultPullStrategy;
import xyz.auriium.tick.docker.source.DockerSource;
import xyz.auriium.tick.docker.source.impl.WindowsSourceProvider;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@EnabledOnOs(OS.WINDOWS) //TODO autoprovider for all platforms
public class BadContainerTest extends BaseTest {

    private static volatile Tick testingTick;
    private static volatile DockerSource notClosedSource;

    @BeforeAll
    public static void startup() {
        testingTick = new CommonTickFactory(
                new HookResourceManager.Provider(false),
                new WindowsSourceProvider(),
                new DefaultPullStrategy.Provider()).produce(); //safe

        notClosedSource = new WindowsSourceProvider().source(CreationOptions.defaults());
    }

    @AfterAll
    public static void teardown() throws IOException {
        testingTick.stop();

        notClosedSource.getClient().close();
    }

    @Test
    public void When_ContainerNameDuplicates_ThrowCE() {


        assertThrows(IllegalStateException.class, () -> {
            testingTick.createContainer(new TinyImageTerms("the-same"));
            testingTick.createContainer(new TinyImageTerms("the-same"));
        });

        assertThrows(NotFoundException.class, () -> {
            testingTick.expose().getClient().inspectContainerCmd("the-same").exec(); //removed
        });


    }

    public void When_BadImage_PurgesSafely() {

        assertThrows(IllegalStateException.class, () -> {
           testingTick.createContainer(new TinyImageTerms("the-same", "hello-world"));
        });

    }

}
