package com.veronica.httpserver.http;

import org.junit.jupiter.api.BeforeAll;
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
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(
                    generateValidGETTestCase()
            );
        } catch (HttpParsingException e) {
            fail(e);
        }
        assertEquals(request.getMethod(), HttpMethod.GET);
    }
    @Test
    void parseHttpRequestBadMethod() throws IOException {
        try {
            HttpRequest request =  httpParser.parseHttpRequest(
                    generateBadTestCase()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }
    @Test
    void parseHttpRequestBadMethod2() throws IOException {
        try {
            HttpRequest request =  httpParser.parseHttpRequest(
                    generateBadTestCase2()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }
    @Test
    void parseHttpRequestLineInvalidItems() throws IOException {
        try {
            HttpRequest request =  httpParser.parseHttpRequest(
                    generateRequestLineInvalidItems()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpEmptyRequestLine() throws IOException {
        try {
            HttpRequest request =  httpParser.parseHttpRequest(
                    generateEmptyRequestLine()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpBadRequestLineOnlyCRNoLF() throws IOException {
        try {
            HttpRequest request =  httpParser.parseHttpRequest(
                    generateBadRequestLineOnlyCRNoLF()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    private InputStream generateValidGETTestCase(){
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
    private InputStream generateBadTestCase() {
        String rawData = "GeT / HTTP/1.1\r\n" +
                "\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }
    private InputStream generateBadTestCase2() {
        String rawData = "GETTTT / HTTP/1.1\r\n" +
                "\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }

    private InputStream generateRequestLineInvalidItems() {
        String rawData = "GET / AAAAAA HTTP/1.1\r\n" +
                "\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }
    private InputStream generateEmptyRequestLine() {
        String rawData = "\r\n" +
                "\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }
    private InputStream generateBadRequestLineOnlyCRNoLF() {
        String rawData = "GET / HTTP/1.1\r" + //no LF
                "\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }
}