package xyz.auriium.tick.driver;

import xyz.auriium.tick.container.ContainerManager;
import xyz.auriium.tick.container.container.JDBCContainer;

import java.sql.*;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class TickDriver implements Driver {

    private final Set<JDBCContainer> containerSet;
    private final ContainerManager manager;

    public TickDriver(Set<JDBCContainer> containerSet, ContainerManager manager) {
        this.containerSet = containerSet;
        this.manager = manager;
    }

    @Override
    public Connection connect(String s, Properties properties) throws SQLException {
        return null;
    }

    @Override
    public boolean acceptsURL(String s) throws SQLException {
        return s.startsWith("jdbc:tick:");
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String s, Properties properties) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
