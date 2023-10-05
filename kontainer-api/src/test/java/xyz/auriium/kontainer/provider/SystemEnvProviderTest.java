package xyz.auriium.kontainer.provider;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import xyz.auriium.kontainer.centralized.CommonTickFactory;
import xyz.auriium.kontainer.centralized.HookResourceManager;
import xyz.auriium.kontainer.centralized.Tick;
import xyz.auriium.kontainer.docker.image.DefaultPullStrategy;
import xyz.auriium.kontainer.docker.source.impl.SystemEnvSourceProvider;

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
