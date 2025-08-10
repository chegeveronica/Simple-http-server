package com.veronica.httpserver;

import com.veronica.httpserver.config.Configuration;
import com.veronica.httpserver.config.ConfigurationManager;
import com.veronica.httpserver.core.ServerListenerThread;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class HttpServer {
    public static void main(String[] args) {

        log.info("Server Starting...");
        ConfigurationManager.getInstance().localConfigurationFile("src/main/resources/http.json");

        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        log.info("Configuration: {}", conf);
        log.info("Using Port: {}", conf.getPort());
        log.info("Using Webroot: {}", conf.getWebroute());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(),conf.getWebroute());
            serverListenerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
