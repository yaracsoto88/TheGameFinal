package Model;

import java.util.ArrayList;


import Controller.TGCT;

public class TGM {
    private ArrayList<Ball> balls ;
    TGCT gamecontroler;

    public TGM(TGCT gamecontroler) {
        this.gamecontroler = gamecontroler;
        this.balls = new ArrayList<>();
        // addBall();
    }

    public void addBall(Ball ball) {
        ball.setModel(this);
        this.balls.add(ball);
        Thread thread = new Thread(ball);
        thread.start();
    }
    public void addBall() {
        Ball newBall = new Ball(this);
        // el Thread se encarga de generar bolas
        Thread thread = new Thread(newBall);
        thread.start();
        this.balls.add(newBall);
    }

    public Boolean collideDetection(Ball ball) {
        int posx = ball.getPosx() + ball.getVx();
        int posy = ball.getPosy() + ball.getVy();

        if (posx > 500) {
            gamecontroler.collide(ball, "x+");
            return true;
        }
        if (posx < 0) {
            gamecontroler.collide(ball, "x-");
            return true;
        }

        if (posy > 500) {
            gamecontroler.collide(ball, "y+");
            return true;
        }
        if (posy < 0) {
            gamecontroler.collide(ball, "y-");
            return true;
        }
       
        return false;
    }

    private void lostBall(Ball ball) {
        // TODO: lostBall

    }

    public void removeBall(Ball ball) {
        this.balls.remove(ball);
    }

    public ArrayList<Ball> getBalls() {
        return this.balls;
    }

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }

}
