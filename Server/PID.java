package Server;

import java.net.Socket;

public class PID implements Runnable {
    private Socket socket;

    public PID(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (socket != null && !socket.isClosed()) {
                System.out.println("Waiting for client " + socket.getInetAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIP() {
        return socket.getInetAddress().getHostAddress();
    }

}
