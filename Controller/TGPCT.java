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

    private CCT connections;
    private TGCT game;
    private String filename = "my.ini";
    private ArrayList<Peer> peers = new ArrayList<>();
    TGR rules;

    public TGPCT() {
        rules = new TGR(this);
        game = new TGCT(this);
        loadConfiguration(filename);
        connections = new CCT(this, peers, 1234);

    }

    public static void main(String[] args) {
        TGPCT game = new TGPCT();
    }

    public void ballRecieved(Ball ball) {
        game.addBall(ball);
    }

    public void collide(Object o1, Object o2) {
        rules.collide(o1, o2);
    }

    public void loadConfiguration(String fileName) {
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] parts = line.split(",");

                String firstString = parts[0];
                int secondNumber = Integer.parseInt(parts[1]);
                String thirdString = parts[2];

                peers.add(new Peer(firstString, secondNumber, PeerLocation.valueOf(thirdString)));

            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendBall(Ball ball, PeerLocation location) {
        if (connections.canSend(location)) {
            ball.kill();
            changePos(ball, location);
            connections.sendBall(ball, location);
        } else {
            changeVelocity(ball, location);
        }
    }

    public void sendLeft(Ball ball) {
        if (connections.canSend(PeerLocation.WEST)) {
            ball.setPosx(499);
            ball.kill();
            connections.sendBall(ball, PeerLocation.WEST);
        } else {
            ball.setVx(-ball.getVx());
        }
    }

    public void sendRight(Ball ball) {
        if (connections.canSend(PeerLocation.EAST)) {
            ball.setPosx(1);
            ball.kill();
            connections.sendBall(ball, PeerLocation.EAST);
        } else {
            ball.setVx(-ball.getVx());
        }
    }

    private void changePos(Ball ball, PeerLocation location) {
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

    private void changeVelocity(Ball ball, PeerLocation location) {
        if (location == PeerLocation.EAST || location == PeerLocation.WEST) {
            ball.setVx(-ball.getVx());
        } else {
            ball.setVy(-ball.getVy());
        }
    }

}
