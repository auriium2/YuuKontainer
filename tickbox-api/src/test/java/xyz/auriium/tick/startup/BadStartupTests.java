package xyz.auriium.tick.startup;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.auriium.tick.centralized.CommonTickFactory;
import xyz.auriium.tick.centralized.HookResourceManager;
import xyz.auriium.tick.docker.image.DefaultPullStrategy;

public class BadStartupTests {

    @Test
    public void badStartupTest() {
        new CommonTickFactory(new HookResourceManager.Provider(false), new BadSourceProvider(), new DefaultPullStrategy.Provider()).produce();
    }

}
