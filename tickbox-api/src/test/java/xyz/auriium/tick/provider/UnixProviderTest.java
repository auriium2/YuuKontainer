package xyz.auriium.tick.provider;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.auriium.tick.CommonTickFactory;
import xyz.auriium.tick.Tick;
import xyz.auriium.tick.TickFactory;
import xyz.auriium.tick.container.container.JDBCContainer;
import xyz.auriium.tick.container.container.MariaDBContainer;
import xyz.auriium.tick.container.terms.mariadb.MariaDBTerms;
import xyz.auriium.tick.docker.image.CachedPullStrategyProvider;
import xyz.auriium.tick.docker.source.impl.UnixSourceProvider;

import java.io.IOException;

@EnabledOnOs({OS.LINUX, OS.MAC})
public class UnixProviderTest {

    private static volatile Tick tick;
    private static final Logger logger = LoggerFactory.getLogger(UnixProviderTest.class);

    @Test
    @BeforeAll
    public static void initializeTick() {
        logger.info("Starting tick using UnixSourceProvider!");

        tick = new CommonTickFactory(new UnixSourceProvider(), new CachedPullStrategyProvider()).produce();
    }

    @Test
    public void createContainer() {

        logger.info("Creating a MariaDB Container!");

        MariaDBTerms terms = new MariaDBTerms("root", "user", "pass",
                "name", "container", 2);

        try (JDBCContainer container = tick.startupContainer(terms)) {
            logger.info("Container started successfully! ID: " + container.getJDBCUrl());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }


}
