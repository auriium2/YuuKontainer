package xyz.auriium.kontainer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger("(TICK TESTING)");

    @BeforeEach
    void beforeEach(TestInfo testInfo) {

        testInfo.getTestClass().orElseThrow().getName();

        logger.info(String.format("\n <<< Test started: %s", testInfo.getDisplayName() + ">>> \n"));
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {

        testInfo.getTestClass().orElseThrow().getName();

        logger.info(String.format("\n\n <<< Test stopped: %s", testInfo.getDisplayName() + ">>> \n"));
    }

}
