package xyz.auriium.kontainer.shutdown;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import xyz.auriium.kontainer.centralized.CommonTickFactory;
import xyz.auriium.kontainer.centralized.HookResourceManager;
import xyz.auriium.kontainer.centralized.Tick;
import xyz.auriium.kontainer.docker.image.DefaultPullStrategy;
import xyz.auriium.kontainer.docker.source.impl.WindowsSourceProvider;

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
