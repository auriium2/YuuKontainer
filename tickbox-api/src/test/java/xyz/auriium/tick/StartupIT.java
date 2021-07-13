package xyz.auriium.tick;


import org.junit.jupiter.api.Test;

/**
 * Test that tests startup and initialization for each existing strategy
 */
public class StartupIT {

    @Test
    public void initializeTick() {
        Tick tick = new CommonTickFactory(null,null,null,null).produce();
    }



}
