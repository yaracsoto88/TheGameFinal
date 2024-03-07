package Server;

import java.net.Socket;
import java.util.ArrayList;
import Controller.TGPCT;
import Data.PeerLocation;
import Interlocutor.Peer;
import Model.Ball;

//TODO el cc tiene que ir mirando los interlocutores y el sc tiene que acpettar las peticiones y ver que PID tiene
public class CCT {
    
    private ArrayList<CH> channels = new ArrayList<>();
    private ArrayList<Peer> peers;
    private final TGPCT controller;
    private SC serverConnector;
    private CC clientConnector;
    public CCT(TGPCT controller, ArrayList<Peer> configurations,int ipServer) {
        this.controller = controller;
        createChannels(configurations);
        this.peers = configurations;
        serverConnector = new SC(this, ipServer);
        clientConnector= new CC(this);
        Thread t = new Thread(serverConnector);
        t.start();
        Thread t2 = new Thread(clientConnector);
        t2.start();
        
    }

    private void createChannels(ArrayList<Peer> configurations) {
        for (Peer peer : configurations) {
            CH ch = new CH(this, peer);
            Thread t = new Thread(ch);
            t.start();
            channels.add(ch);
        }
    }

    public synchronized void addSocket(Socket socket,CH ch)  throws Exception{
        if(ch.isConnected()){
            return;
        }
        ch.initChanel(socket);
       
    }

    public void addChannelServer(Socket socket) {
        for (CH ch : channels) {
            if (!ch.isConnected() ) {
                try {
                    this.addSocket(socket, ch);
                } catch (Exception e) {
                    System.out.println("Error en el CCT: " + e);
                }
                return;
            }
            }
        }


    public boolean canSend(PeerLocation location) {
        
        for(CH ch: channels){
        
            if(ch.getPeer().getLocation()==location && ch.isConnected()){
                return true;
            }
        }
        return false;
    }

    public void enviarBall(Ball ball, PeerLocation location) {
        for(CH ch: channels){
            if(ch.getPeer().getLocation()==location && ch.isConnected()){
                System.out.println("Enviando bola a: "+ch.getPeer().getLocation());
                ch.sendBall(ball);
                return;
            }
        }
    }

    public void recibirBall(Ball ball) {
        controller.ballRecieved(ball);;
    }

    public ArrayList<CH> getChannels() {
        return channels;
    }

   
}
