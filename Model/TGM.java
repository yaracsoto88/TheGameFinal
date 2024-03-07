package Model;

import java.util.ArrayList;

import Controller.TGCT;

public class TGM {
    private ArrayList<Ball> ballList;
    TGCT gamecontroller;

    public TGM(TGCT gamecontroller) {
        this.gamecontroller = gamecontroller;
        this.ballList = new ArrayList<>();
    }

    public void addBall(Ball ball) {
        ball.setModel(this);
        this.ballList.add(ball);
        Thread thread = new Thread(ball);
        thread.start();
    }

    public void addBall() {
        Ball newBall = new Ball(this);
        Thread thread = new Thread(newBall);
        thread.start();
        this.ballList.add(newBall);
    }

    public Boolean collideDetection(Ball ball) {
        int posx = ball.getPosx() + ball.getVx();
        int posy = ball.getPosy() + ball.getVy();

        if (posx > 500) {
            gamecontroller.collide(ball, "x+");
            return true;
        }
        if (posx < 0) {
            gamecontroller.collide(ball, "x-");
            return true;
        }

        if (posy > 500) {
            gamecontroller.collide(ball, "y+");
            return true;
        }
        if (posy < 0) {
            gamecontroller.collide(ball, "y-");
            return true;
        }

        return false;
    }

    public void removeBall(Ball ball) {
        this.ballList.remove(ball);
    }

    public ArrayList<Ball> getBallList() {
        return this.ballList;
    }

    public void setBallList(ArrayList<Ball> balls) {
        this.ballList = balls;
    }

}
