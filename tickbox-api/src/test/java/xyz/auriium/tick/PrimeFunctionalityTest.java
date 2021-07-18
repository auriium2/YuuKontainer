package xyz.auriium.tick;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.auriium.tick.centralized.CommonTickFactory;
import xyz.auriium.tick.centralized.HookResourceManager;
import xyz.auriium.tick.centralized.Tick;
import xyz.auriium.tick.docker.image.CommonPoolStrategy;
import xyz.auriium.tick.docker.source.impl.WindowsSourceProvider;

public class PrimeFunctionalityTest {

    private static volatile Tick tick;

    @BeforeAll
    public static void startup() {
        tick = new CommonTickFactory(
                new HookResourceManager.Provider(true),
                new WindowsSourceProvider(),
                new CommonPoolStrategy.Provider()).produce();
    }

    @Test
    public void test() {

    }

    @AfterAll
    public static void teardown() {
        tick.stop();
    }
}
