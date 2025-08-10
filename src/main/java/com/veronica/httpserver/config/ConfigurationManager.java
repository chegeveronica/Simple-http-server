package com.veronica.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.veronica.httpserver.utils.Json;
import lombok.RequiredArgsConstructor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RequiredArgsConstructor
public class ConfigurationManager {
    private static ConfigurationManager configurationManager;
    private static Configuration configuration;

    public static ConfigurationManager getInstance(){
        if (configurationManager == null)
            configurationManager = new ConfigurationManager();
        return configurationManager;

    }

    //used to load a configuration file by the path provided
    public void localConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }

        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read()) != -1){
                sb.append((char) i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error Parsing the Configuration File", e);
        }
        try {
            configuration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error Parsing the Configuration File, Internal", e);
        }
    }
    
    //returns the current loaded configuration
    public Configuration getCurrentConfiguration(){
        if (configuration == null){
            throw new HttpConfigurationException("No Current Configuration Set");
        }
        return configuration;

    }
}