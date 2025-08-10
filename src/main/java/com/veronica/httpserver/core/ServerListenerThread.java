package com.veronica.httpserver.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@RequiredArgsConstructor
@Slf4j
public class ServerListenerThread extends Thread {

    private ServerSocket serverSocket;

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {

        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                log.info("* Connection Accepted: {}", socket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }

            } catch (IOException e) {
            log.error("Problem with Setting Socket ...", e);
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException _) {}
            }
        }
    }

}
