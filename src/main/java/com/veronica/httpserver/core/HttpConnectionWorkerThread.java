package com.veronica.httpserver.core;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
public class HttpConnectionWorkerThread extends Thread {

    private final Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
//
//            int _byte;
//
//            while((_byte = inputStream.read()) >= 0 ){
//                System.out.println((char) _byte);
//            }

            String html = "<html>\n" +
                    "<body>\n" +
                    "\n" +
                    "<h1>My First Heading</h1>\n" +
                    "<p>My first paragraph.</p>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";

            final String CRLF = "\n\r"; //13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF + //Status line with HTTP VERSION STATUS CODE AND RESPONSE MESSAGE
                            "Content-length: " + html.getBytes().length + CRLF + //header
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());

            log.info("Connection Processing Finished");
        } catch (IOException e) {
            log.error("Problem with Communication", e);
        }finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException _) {}
            }
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException _) {}
            }
            if(socket!=null) {
                try {
                    socket.close();
                } catch (IOException _) {}
            }
        }
    }
}