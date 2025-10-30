package com.veronica.httpserver.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class HttpParser {

    private static final int SP = 0X20; //32
    private static final int CR = 0X0D; //14
    private static final int LF = 0X0A; //10


    public HttpRequest parseHttpRequest(InputStream inputStream) throws IOException, HttpParsingException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest request = new HttpRequest();

        try {
            parseRequestLine(reader,request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parseHeaders(reader,request);
        parseBody(reader,request);

        return request;
    }
    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        StringBuilder dataProcessingBuffer = new StringBuilder();

        boolean methodParsed = false;
        boolean targetRequestParsed = false;

        int _byte;
        while ((_byte = reader.read()) >=0){
            if(_byte == CR){
                _byte = reader.read();
                if (_byte == LF){
                    log.info("Request line VERSION to process: {}", dataProcessingBuffer.toString());
                    if(!methodParsed || !targetRequestParsed){
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    return;
                }
            }
            if (_byte == SP){
                if (!methodParsed){
                    log.info("Request line METHOD to process: {}", dataProcessingBuffer.toString());
                    request.setMethod(dataProcessingBuffer.toString());
                    methodParsed = true;
                }else if (!targetRequestParsed){
                    log.info("Request line TARGET REQ to process: {}", dataProcessingBuffer.toString());
                    targetRequestParsed = true;
                }else{
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                dataProcessingBuffer.delete(0, dataProcessingBuffer.length());

            }else{
                dataProcessingBuffer.append((char)_byte);
                if(!methodParsed){
                    if(dataProcessingBuffer.length() > HttpMethod.MAX_LENGTH){
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }
    private void parseHeaders(InputStreamReader reader, HttpRequest request) {
    }

    private void parseBody(InputStreamReader reader, HttpRequest request) {
    }

}
