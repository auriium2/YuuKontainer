package me.aurium.tick.docker.network;

public interface Network extends AutoCloseable{

    String getNetworkName();

}
