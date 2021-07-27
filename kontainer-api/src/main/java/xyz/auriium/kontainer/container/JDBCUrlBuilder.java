package xyz.auriium.kontainer.container;

import java.util.Objects;

public class JDBCUrlBuilder {

    private String driverName;
    private String ip;
    private int port;
    private String dbName;

    public JDBCUrlBuilder withDriver(String name) {
        this.driverName = name;
        return this;
    }

    public JDBCUrlBuilder withIP(String ip) {
        this.ip = ip;
        return this;
    }

    public JDBCUrlBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public JDBCUrlBuilder withDBName(String name) {
        this.dbName = name;
        return this;
    }

    public String build() {
        Objects.requireNonNull(dbName);
        Objects.requireNonNull(ip);
        Objects.requireNonNull(driverName);

        return "jdbc:" + driverName + "://" + ip + ":" + port + "/" + dbName;
    }

}
