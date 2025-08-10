package com.veronica.httpserver.http;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpParser {

    private static final int SP = 0X20; //32
    private static final int CR = 0X0D; //14
    private static final int LF = 0X0A; //10


    public HttpRequest parseHttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest request = new HttpRequest();

        parseRequestLine(reader,request);
        parseHeaders(reader,request);
        parseBody(reader,request);

        return request;
    }
    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException {
        int _byte;
        while ((_byte = reader.read()) >=0){
            if(_byte == CR){
                _byte = reader.read();
                if (_byte == LF){
                    return;
                }
            }
        }
    }
    private void parseHeaders(InputStreamReader reader, HttpRequest request) {
    }

    private void parseBody(InputStreamReader reader, HttpRequest request) {
    }

}
