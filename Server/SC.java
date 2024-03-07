package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SC implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private CCT controller;

    public SC(CCT controller, int port) {
        this.controller = controller;
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    public void run() {
        try {
            this.clientSocket = serverSocket.accept();
            controller.addChannelServer(clientSocket);
            PID pid = new PID(clientSocket);
            Thread t = new Thread(pid);
            t.start();
        } catch (Exception e) {
            System.out.println("ServerConnector error: " + e);
        }
    }

    public void killSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSocketClosed() {
        return serverSocket.isClosed();
    }
}