package xyz.auriium.tick.shutdown;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import xyz.auriium.tick.centralized.CommonTickFactory;
import xyz.auriium.tick.centralized.HookResourceManager;
import xyz.auriium.tick.centralized.Tick;
import xyz.auriium.tick.docker.image.DefaultPullStrategy;
import xyz.auriium.tick.docker.source.impl.WindowsSourceProvider;

@Disabled //TODO how do we mock system?
public class ShutdownHookTest {

    private static volatile Tick tick;

    @BeforeAll
    public static void startup() {
        tick = new CommonTickFactory(
                new HookResourceManager.Provider(true),
                new WindowsSourceProvider(),
                new DefaultPullStrategy.Provider()).produce();
    }







}
