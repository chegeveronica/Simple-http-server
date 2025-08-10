package com.veronica.httpserver.config;

public class Configuration {
      private int port;
      private String webroute;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWebroute() {
        return webroute;
    }

    public void setWebroute(String webroute) {
        this.webroute = webroute;
    }
}
