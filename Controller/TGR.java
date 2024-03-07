package Controller;

import Data.PeerLocation;
import Model.Ball;

public class TGR {
    TGPCT controler;

    public TGR(TGPCT controler) {
        this.controler = controler;
    }

    public void collide(Object o1, Object o2) {
        if (o1 instanceof Ball && o2 instanceof String) {
            collide((Ball) o1, (String) o2);
        }
        if (o1 instanceof Ball && o2 instanceof Ball) {
            collide((Ball) o1, (Ball) o2);
        }
    }

    private void collide(Ball ball, String wall) {
        if (wall.equals("y+")) {
           controler.enviarBola(ball,PeerLocation.NORTH);
            return;
        }
        if (wall.equals("y-")) {
            controler.enviarBola(ball,PeerLocation.SOUTH);
            return;
        }
        if (wall.equals("x+")) {
            controler.enviarBola(ball,PeerLocation.EAST);
            return;
        }
        if (wall.equals("x-")) {
            controler.enviarBola(ball,PeerLocation.WEST);
            return;
        }

    }

    private void collide(Ball b1, Ball b2) {
        b1.kill();
        b2.kill();
    }
}
