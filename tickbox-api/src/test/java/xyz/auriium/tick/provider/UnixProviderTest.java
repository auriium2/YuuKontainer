package xyz.auriium.tick.provider;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import xyz.auriium.tick.BaseTest;
import xyz.auriium.tick.centralized.CommonTickFactory;
import xyz.auriium.tick.centralized.HookResourceManager;
import xyz.auriium.tick.centralized.Tick;
import xyz.auriium.tick.container.TickContainer;
import xyz.auriium.tick.container.TinyImageTerms;
import xyz.auriium.tick.docker.image.DefaultPullStrategy;
import xyz.auriium.tick.docker.source.impl.UnixSourceProvider;
import xyz.auriium.tick.docker.source.impl.WindowsSourceProvider;

@EnabledOnOs(OS.LINUX)
public class UnixProviderTest extends BaseTest {

    private static volatile Tick tick;

    @BeforeAll
    public static void startup() {
        tick = new CommonTickFactory(
                new HookResourceManager.Provider(false),
                new UnixSourceProvider(),
                new DefaultPullStrategy.Provider()).produce();
    }

    @AfterAll
    public static void teardown() {
        tick.stop();
    }

    @Test
    public void whenCreated_ContainerWorksFine() {
        TickContainer container = tick.createContainer(new TinyImageTerms("unix-test"));
    }
}
