package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.util.Random;

public class Ball implements Runnable, VO, Serializable {
    private int vx, vy;
    private int posx, posy, radius;
    private boolean isRunning = true;
    private Color color;
    Random ran = new Random();
    TGM model;

    public Ball(int vx, int vy, int posx, int posy, int radius) {
        this.vx = vx;
        this.vy = vy;
        this.posx = posx;
        this.posy = posy;
        this.radius = radius;
    }

    public Ball() {
    }

    public Ball(Ball ball) {
        this.vx = ball.getVx();
        this.vy = ball.getVy();
        this.posx = ball.getPosx();
        this.posy = ball.getPosy();
        this.radius = ball.getRadius();
        this.color = ball.getColor();
    }

    public Ball(TGM model) {
        this.model = model;
        this.vx = ran.nextInt(2, 4);
        this.vy = ran.nextInt(2, 4);
        this.posx = ran.nextInt(500);
        this.posy = ran.nextInt(500);
        this.radius = ran.nextInt(8, 22);
        this.color = new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
    }

    public void kill() {
        this.isRunning = false;
        model.removeBall(this);
    }
    
    @Override
    public void run() {
        while (isRunning) {
            if (!model.collideDetection(this)) {
                move();
            }
            try {
                {
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(this.color);
        g2d.fillOval(posx, posy, radius, radius); 
    }

    @Override
    public void move() {
        posx += vx;
        posy += vy;
    }

    public int getVx() {
        return this.vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return this.vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getPosx() {
        return this.posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return this.posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public int getRadius() {
        return this.radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isIsRunning() {
        return this.isRunning;
    }

    public boolean getIsRunning() {
        return this.isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRan(Random ran) {
        this.ran = ran;
    }

    public TGM getModel() {
        return this.model;
    }

    public void setModel(TGM model) {
        this.model = model;
    }

}
