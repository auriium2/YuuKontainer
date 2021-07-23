package xyz.auriium.tick;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.auriium.tick.centralized.CommonTickFactory;
import xyz.auriium.tick.centralized.HookResourceManager;
import xyz.auriium.tick.centralized.Tick;
import xyz.auriium.tick.container.AlpineTerms;
import xyz.auriium.tick.container.TickContainer;
import xyz.auriium.tick.docker.image.DefaultPullStrategy;
import xyz.auriium.tick.docker.source.impl.WindowsSourceProvider;

public class WindowsProviderTest {

    private static volatile Tick tick;

    @BeforeAll
    public static void startup() {
        tick = new CommonTickFactory(
                new HookResourceManager.Provider(true),
                new WindowsSourceProvider(),
                new DefaultPullStrategy.Provider()).produce();
    }

    @Test
    public void test() {
        TickContainer container = tick.createContainer(new AlpineTerms("the-cheese-man"));
    }

    @AfterAll
    public static void teardown() {
        tick.stop();
    }
}
