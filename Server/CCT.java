package Server;

import java.net.Socket;
import java.util.ArrayList;
import Controller.TGPCT;
import Data.PeerLocation;
import Interlocutor.Peer;
import Model.Ball;

public class CCT {
    private ArrayList<CH> channels = new ArrayList<>();
    private ArrayList<Peer> peers;
    private final TGPCT controller;
    private SC serverConnector;
    private CC clientConnector;

    public CCT(TGPCT controller, ArrayList<Peer> configurations, int ipServer) {
        this.controller = controller;
        createChannels(configurations);
        this.peers = configurations;
        serverConnector = new SC(this, ipServer);
        clientConnector = new CC(this);
        Thread t = new Thread(serverConnector);
        t.start();
        Thread t2 = new Thread(clientConnector);
        t2.start();

    }

    public void addChannelServer(Socket socket) {
        for (CH ch : channels) {
            if (!ch.isConnected()) {
                try {
                    this.addSocket(socket, ch);
                } catch (Exception e) {
                    System.out.println("Error " + e);
                }
                return;
            }
        }
    }

    public synchronized void addSocket(Socket socket, CH ch) throws Exception {
        if (ch.isConnected()) {
            return;
        }
        ch.initChanel(socket);
    }

    private void createChannels(ArrayList<Peer> configurations) {
        for (Peer peer : configurations) {
            CH ch = new CH(this, peer);
            Thread t = new Thread(ch);
            t.start();
            channels.add(ch);
        }
    }

    public boolean canSend(PeerLocation location) {
        for (CH ch : channels) {
            if (ch.getPeer().getLocation() == location && ch.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public void reciveBall(Ball ball) {
        controller.ballRecieved(ball);
    }

    public void sendBall(Ball ball, PeerLocation location) {
        for (CH ch : channels) {
            if (ch.getPeer().getLocation() == location && ch.isConnected()) {
                ch.sendBall(ball);
                return;
            }
        }
    }

    public ArrayList<CH> getChannels() {
        return channels;
    }

}
