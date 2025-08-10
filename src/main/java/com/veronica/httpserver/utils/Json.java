package com.veronica.httpserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {

    private static ObjectMapper objectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper on = new ObjectMapper();
        on.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //ensures it doesn't throw error in case of some data missing
        return on;
    }

    public static JsonNode parse(String jsonSrc) throws IOException {
        return objectMapper.readTree(jsonSrc);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return objectMapper.treeToValue(node,clazz);
    }
    
    public static JsonNode toJson(Object obj){
        return objectMapper.valueToTree(obj);
    }

    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }

    //helps view json in a string format
    private static String generateJson(Object o, Boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        if(pretty){
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }

        return objectWriter.writeValueAsString(o);
    }
}
