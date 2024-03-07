package Server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import Interlocutor.Peer;
import Model.*;

/*
Clase de conexión, se encarga de la comunicación entre el cliente y el servidor.
*/
public class CH implements Runnable {
    private Socket socket;
    private TCH hcc;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private CCT controller;
    private long timeReceivedMessage;
    private volatile boolean runState = true;
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
        this.hcc = healthCareConnection;
        this.connected = true;
    }

    public void sendBall(Ball ball) {
        try {
            Balldata ballData = new Balldata(ball);
            out.writeObject(ballData);
            out.flush();
        } catch (Exception e) {
            System.out.println(
                    "hay un error :(" + "\n" + "Codigo de error: " + e);
        }
    }

    public Peer getPeer() {
        return this.peer;
    }

    public void recieveBall() {

        try {
            Object object = in.readObject();
            System.out.println("Recibido ping");
            if (object instanceof String && object.equals("ping")) {
                System.out.println("Recibido ping");
                return;
            }
            // System.out.println("Recibido bolas");
            Balldata m = (Balldata) object;
            controller.recibirBall(m.transformData());
            long time = (System.currentTimeMillis());
            setTimeReceivedMessage(time);

        } catch (Exception e) {
            System.err.println("Error en la conexion");
            killSocket();
        }
    }

    public void setTimeReceivedMessage(long timeReceivedMessage) {
        this.timeReceivedMessage = timeReceivedMessage;
    }

    public void setHCC(TCH hch) {
        this.hcc = hch;
    }

    public boolean ping() {
        try {
            // System.out.println("entra en ping");
            out.writeObject(new String("ping"));
            return true;
        } catch (Exception e) {
            System.out.println("Error en el envío del heartbeat: " + e);
            return false;
        }
    }

    public void run() {
        while (runState) {
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
                // Verificar si la conexión actual está cerrada
                if (socket == null || socket.isClosed() || !socket.isConnected()) {
                    System.out.println("Conexión perdida, intentando reconectar...");
                    this.connected = false;
                    Thread.sleep(1000);

                }
                // Leer mensajes entrantes
                System.out.println(this.connected + " " + "conectado");
                if (socket != null && socket.isConnected()) {
                    recieveBall();
                }

                // Esperar un poco antes de verificar la conexión de nuevo
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Error en la reconexión: " + e);
            }
        }

    }

    public long getTimeReceivedMessage() {
        return timeReceivedMessage;
    }

    public void killSocket() {
        try {
            this.connected = false;

        } catch (Exception e) {
            System.out.println("Error en la desconexión: " + e);
        }
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

    @Override
    public String toString() {
        return "esto existe";
    }

    public TCH getHcc() {
        return this.hcc;
    }

    public void setHcc(TCH hcc) {
        this.hcc = hcc;
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