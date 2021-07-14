package xyz.auriium.tick;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.docker.image.CachedPullStrategyProvider;
import xyz.auriium.tick.docker.source.DockerSourceProvider;
import xyz.auriium.tick.docker.source.impl.ManualSourceProvider;
import xyz.auriium.tick.docker.source.impl.SystemEnvSourceProvider;

import java.util.stream.Stream;

/**
 * Test that tests startup and initialization for each existing strategy
 */
public class TickCreationTest {

    private static final Logger logger = LoggerFactory.getLogger(TickCreationTest.class);

    @BeforeEach
    void setUp(TestInfo testInfo) {
        logger.info(String.format("test started: %s", testInfo.getDisplayName());
    }
    @AfterEach
    void tearDown(TestInfo testInfo) {
        logger.info(String.format("test finished: %s", testInfo.getDisplayName());
    }

    @Test
    public void shouldHandleBadProvider() {
        Assertions.assertThrows
        Tick tick = new CommonTickFactory(new ManualSourceProvider("fail"), )
    }

    @ParameterizedTest
    @MethodSource("strategyParams")
    public void produceTick(DockerSourceProvider provider) {
        Tick tick = new CommonTickFactory(provider,new CachedPullStrategyProvider()).produce();
    }

    private static Stream<Arguments> strategyParams() {
        return Stream.of(
                Arguments.of(new SystemEnvSourceProvider())
        );
    }



}
