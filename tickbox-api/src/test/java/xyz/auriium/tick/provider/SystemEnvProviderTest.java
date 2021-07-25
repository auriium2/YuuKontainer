package xyz.auriium.tick.provider;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xyz.auriium.tick.centralized.CommonTickFactory;
import xyz.auriium.tick.centralized.HookResourceManager;
import xyz.auriium.tick.centralized.Tick;
import xyz.auriium.tick.docker.image.DefaultPullStrategy;
import xyz.auriium.tick.docker.source.impl.SystemEnvSourceProvider;

@Disabled //TODO mocking system envs
public class SystemEnvProviderTest {

    @Test
    public void startup() {
        Tick tick = new CommonTickFactory(
                new HookResourceManager.Provider(false),
                new SystemEnvSourceProvider(),
                new DefaultPullStrategy.Provider()).produce();
    }

}
