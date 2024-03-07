package Interlocutor;

import Data.PeerLocation;

public class Peer extends Interlocutor {
    private PeerLocation location;

    public Peer(String ip, int port, PeerLocation location) {
        super(ip, port);
        this.location = location;
    }

    public PeerLocation getLocation() {
        return this.location;
    }

    public void setLocation(PeerLocation location) {
        this.location = location;
    }

}
