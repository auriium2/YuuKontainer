package me.aurium.tick.container.jdbc;

import me.aurium.tick.container.TickContainer;

public interface JDBCTickContainer extends TickContainer {

    String getJDBCUrl();

    String getExposedJDBCUrl(); //figure this out later

}
