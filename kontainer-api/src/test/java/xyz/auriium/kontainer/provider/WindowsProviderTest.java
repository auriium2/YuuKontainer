package xyz.auriium.kontainer.provider;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import xyz.auriium.kontainer.BaseTest;
import xyz.auriium.kontainer.centralized.CommonTickFactory;
import xyz.auriium.kontainer.centralized.HookResourceManager;
import xyz.auriium.kontainer.centralized.Tick;
import xyz.auriium.kontainer.container.TickContainer;
import xyz.auriium.kontainer.container.TinyImageTerms;
import xyz.auriium.kontainer.docker.image.DefaultPullStrategy;
import xyz.auriium.kontainer.docker.source.impl.WindowsSourceProvider;

@EnabledOnOs(OS.WINDOWS)
public class WindowsProviderTest extends BaseTest {

    private static volatile Tick tick;

    @BeforeAll
    public static void startup() {


        tick = new CommonTickFactory(
                new HookResourceManager.Provider(false),
                new WindowsSourceProvider(),
                new DefaultPullStrategy.Provider()).produce();
    }

    @AfterAll
    public static void teardown() {
        tick.stop();
    }

    @Test
    public void whenCreated_ContainerWorksFine() {
        TickContainer container = tick.createContainer(new TinyImageTerms("windows-test"));
    }


}
