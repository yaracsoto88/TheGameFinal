package Model;

import java.awt.Color;
import java.io.Serializable;

public class Balldata implements Serializable{
    private int vx, vy;
    private int posx, posy, radius;
    private Color color;
    
    public Balldata(Ball ball){
        this.vx = ball.getVx();
        this.vy = ball.getVy();
        this.posx = ball.getPosx();
        this.posy = ball.getPosy();
        this.radius = ball.getRadius();
        this.color = ball.getColor();
    }

    public Ball transformBall(){
        Ball ball = new Ball();
        ball.setVx(this.vx);
        ball.setVy(this.vy);
        ball.setPosx(this.posx);
        ball.setPosy(this.posy);
        ball.setRadius(this.radius);
        ball.setColor(this.color);
        return ball;
    }
    @Override
    public String toString(){
        return "Ball: "+this.posx+" "+this.posy+" "+this.radius+" "+this.vx+" "+this.vy+" "+this.color;
    }
}
