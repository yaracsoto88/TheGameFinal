package Server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import Interlocutor.Peer;
import Model.*;

public class CH implements Runnable {
    private Socket socket;
    private TCH tch;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private CCT controller;
    private long timeReceivedMessage;
    private volatile boolean state = true;
    private String ip;
    private int port;
    private Peer peer;
    private boolean connected;

    public CH(CCT controller, Peer peer) {
        this.connected = false;
        this.peer = peer;
        this.controller = controller;
    }

    public void initChanel(Socket socket) throws Exception {
        this.socket = socket;
        OutputStream os = socket.getOutputStream();
        this.out = new ObjectOutputStream(os);
        InputStream is = socket.getInputStream();
        this.in = new ObjectInputStream(is);

        TCH healthCareConnection = new TCH(this, 10000);
        new Thread(healthCareConnection).start();
        this.tch = healthCareConnection;
        this.connected = true;
    }

    public void killSocket() {
        try {
            this.connected = false;

        } catch (Exception e) {
            System.out.println("Error disconnecting: " + e);
        }
    }

    public boolean ping() {
        try {
            out.writeObject(new String("ping"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void recieveBall() {
        try {
            Object object = in.readObject();

            if (object instanceof String && object.equals("ping")) {
                System.out.println("Ping received");

                return;
            }

            Balldata m = (Balldata) object;
            controller.reciveBall(m.transformBall());
            long time = (System.currentTimeMillis());
            setTimeReceivedMessage(time);

        } catch (Exception e) {
            System.err.println("Error in recieveBall: " + e);
            killSocket();
        }
    }

    public void run() {
        while (state) {
            if (!connected) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            try {
                Thread.sleep(1000);

                if (socket == null || socket.isClosed() || !socket.isConnected()) {
                    System.out.println("connection lost, trying to reconnect...");
                    this.connected = false;
                    Thread.sleep(1000);

                }
                System.out.println(this.connected + "connected");
                if (socket != null && socket.isConnected()) {
                    recieveBall();
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendBall(Ball ball) {
        try {
            Balldata ballData = new Balldata(ball);
            out.writeObject(ballData);
            out.flush();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    public void setHCC(TCH hch) {
        this.tch = hch;
    }

    public void setTimeReceivedMessage(long timeReceivedMessage) {
        this.timeReceivedMessage = timeReceivedMessage;
    }

    public Peer getPeer() {
        return this.peer;
    }

    @Override
    public String toString() {
        return "CH [socket=" + socket + ", tch=" + tch + ", in=" + in + ", out=" + out + ", controller=" + controller
                + ", timeReceivedMessage=" + timeReceivedMessage + ", state=" + state + ", ip=" + ip + ", port=" + port
                + ", peer=" + peer + ", connected=" + connected + "]";
    }

    public long getTimeReceivedMessage() {
        return timeReceivedMessage;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setObjectOutputStream(ObjectOutputStream out) {
        this.out = out;
    }

    public void setObjectInputStream(ObjectInputStream in) {
        this.in = in;
    }

    public TCH getTch() {
        return this.tch;
    }

    public void setTch(TCH tch) {
        this.tch = tch;
    }

    public ObjectInputStream getIn() {
        return this.in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public ObjectOutputStream getOut() {
        return this.out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public CCT getController() {
        return this.controller;
    }

    public void setController(CCT controller) {
        this.controller = controller;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnected() {
        return this.connected;
    }

}