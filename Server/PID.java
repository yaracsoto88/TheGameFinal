package Server;

import java.net.Socket;

public class PID implements Runnable{
    private Socket socket;

    public PID(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            while (socket!=null && !socket.isClosed()) {
                System.out.println("Esperando mensaje del cliente: " + socket.getInetAddress());                
            }
        } catch (Exception e) {
            System.out.println("Error en el PID: " + e);
        }
    }

    public String getIP() {
        return socket.getInetAddress().getHostAddress();
    }

   
    
}
