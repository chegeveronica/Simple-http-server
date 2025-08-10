package com.veronica.httpserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest() throws IOException {
        httpParser.parseHttpRequest(
                generateValidTestCase()
        );
    }

    private InputStream generateValidTestCase(){
        String rawData = "GET / HTTP/1.1\r\n" +
                "\r\n" +
                "\r\n" +
                "Host: localhost:8080\r\n" +
                "\r\n" +
                "\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:139.0) Gecko/20100101 Firefox/13\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
                "\r\n" +
                "\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "\r\n" +
                "Accept-Encoding: gzip, deflate, br, z\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                "\r\n" +
                "Connection: keep-alive\r\n" +
                "Cookie:Idea-4fdd8a88=f89d7426-842a-4197-aa72-f5bca3226b3c\r\n" +
                "\r\n" +
                "Upgrade-Insecure-Requests:1\r\n" +
                "Sec-Fetch-Dest:document\r\n" +
                "Sec-Fetch-Mode:navigate\r\n" +
                "Sec-Fetch-Site:none\r\n" +
                "Sec-Fetch-User:?1\r\n" +
                "Priority:u=0,i\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }
}