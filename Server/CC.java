package Server;

import java.net.Socket;

/*Conector para actuar como cliente*/

public class CC implements Runnable {
    private CCT controller;

    public CC(CCT controller) {
        this.controller = controller;

    }

    @Override
    public void run() {
        while (true) {
            
            try {
                Thread.sleep(10000);
                createConnection();
            } catch (Exception e) {
                System.out.println("conectando");
            }
        }
    }

    private void createConnection() {
        for (CH channel : this.controller.getChannels()) {
            if (!channel.isConnected()) {
                try {
                    Socket socket = new Socket(channel.getPeer().getIp(), channel.getPeer().getPort());
                    System.out.println("Conectado a: " + channel.getPeer().getIp()+ channel.getPeer().getPort());
                    System.out.println("Conectado a CC: " + socket);
                    controller.addSocket(socket, channel);
                    break;
                } catch (Exception e) {
                    System.out.println("Error en el CC: " + e);
                }
            }
        }
    }

}
