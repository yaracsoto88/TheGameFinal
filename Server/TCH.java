package Server;

public class TCH implements Runnable {

    private CH connection;

    private long timeOut;

    public boolean isOn;

    public TCH(CH connection, long timeOut) {
        this.connection = connection;
        this.timeOut = timeOut;
        isOn = true;
    }

    public void pararEjecucion() {
        System.out.println("Stopping executio of Health Care");
        isOn = false;
    }

    public void run() {
        while (isOn) {
            long currentTime = System.currentTimeMillis();
            long timeLastMessage = connection.getTimeReceivedMessage();
            long difference = currentTime - timeLastMessage;

            if (difference > timeOut) {
                if (connection.ping()) {
                    System.out.println("Health Care: Ping sent successfully");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Closing Connection.");
                    connection.killSocket();
                    break;
                }
            }
        }
    }

}
