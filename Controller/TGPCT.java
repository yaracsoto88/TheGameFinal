package Controller;

import Server.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Data.PeerLocation;
import Interlocutor.Peer;
import Model.*;

public class TGPCT {

    private CCT conexiones;
    private TGCT game;
    private String filename = "my.ini";
    private ArrayList<Peer> peers = new ArrayList<>();
    TGR rules;

    public TGPCT() {
        rules = new TGR(this);
        game = new TGCT(this);
        loadConfiguration(filename);
        conexiones = new CCT(this, peers,1235);

    }

    public static void main(String[] args) {
        TGPCT game = new TGPCT();
    }
    
    public void collide(Object o1, Object o2) {
        rules.collide(o1, o2);
    }

    public void enviarBola(Ball ball, PeerLocation location) {
        if (conexiones.canSend(location)) {
            ball.kill();
            changePosition(ball, location);
            conexiones.enviarBall(ball, location);
        } else {
            changeVelocity(ball, location);
        }
    }

    private void changePosition(Ball ball, PeerLocation location) {
        switch (location) {
            case EAST:
                ball.setPosx(1);
                break;
            case NORTH:
                ball.setPosy(1);
                break;
            case SOUTH:
                ball.setPosy(499);
                break;
            case WEST:
                ball.setPosx(499);
                break;
            default:
                break;
        }
    }

    private void changeVelocity(Ball ball, PeerLocation location){
        if(location == PeerLocation.EAST || location == PeerLocation.WEST){
            ball.setVx(-ball.getVx());
        }else{
            ball.setVy(-ball.getVy());
        }
    }

    public void enviarDerecha(Ball ball) {
        if (conexiones.canSend(PeerLocation.EAST)) {
            ball.setPosx(1);
            ball.kill();
            conexiones.enviarBall(ball, PeerLocation.EAST);
        } else {
            System.err.println("tiene que rebotar");
            ball.setVx(-ball.getVx());
        }
    }

    public void enviarIzquierda(Ball ball) {
        if (conexiones.canSend( PeerLocation.WEST)) {
            ball.setPosx(499);
            ball.kill();
            conexiones.enviarBall(ball, PeerLocation.WEST);
        } else {
            ball.setVx(-ball.getVx());
        }
    }

    public void ballRecieved(Ball ball) {
        game.addBall(ball);
    }

    public void loadConfiguration(String fileName) {
        File archivo = new File(fileName);
        try {
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {

                String linea = scanner.nextLine();

                String[] partes = linea.split(",");

                String primerString = partes[0];
                int segundoInt = Integer.parseInt(partes[1]);
                String tercerString = partes[2];

                peers.add(new Peer(primerString, segundoInt, PeerLocation.valueOf(tercerString)));

            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
