package Server;

import java.net.Socket;

public class CC implements Runnable {
    private CCT controller;

    public CC(CCT controller) {
        this.controller = controller;

    }

    private void createConnection() {
        for (CH channel : this.controller.getChannels()) {
            if (!channel.isConnected()) {
                try {
                    Socket socket = new Socket(channel.getPeer().getIp(), channel.getPeer().getPort());
                    System.out.println("Connected to: " + channel.getPeer().getIp() + channel.getPeer().getPort());
                    controller.addSocket(socket, channel);
                    break;
                } catch (Exception e) {
                    System.out.println("Error " + e);
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(10000);
                createConnection();
            } catch (Exception e) {
                System.out.println("Connecting");
            }
        }
    }

}
