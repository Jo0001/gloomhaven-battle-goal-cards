package de.jo0001.gloomhaven.battleGoalCards.other;

import java.io.Serializable;

public class DataHolder implements Serializable {
    private int port;
    private String host;

    public DataHolder() {
    }

    public DataHolder(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Data{" +
                "port=" + port +
                ", host='" + host + '\'' +
                '}';
    }
}
