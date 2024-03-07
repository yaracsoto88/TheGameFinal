package Controller;

import Model.Ball;
import Model.TGM;
import View.TGV;

public class TGCT {
    TGV view;
    TGM model;
    TGPCT controler;
    public TGCT(TGPCT controler) {
        this.controler = controler;
        this.model = new TGM(this);
        this.view = new TGV(this);
    }

    public void play() {
        // cuando llamamos a addBall se crea una nueva bola y luego se incia un nuevo
        // hilo para ejecutar esa misma bola
        this.model.addBall();
    }


    public TGV getView() {
        return this.view;
    }

    public void setView(TGV view) {
        this.view = view;
    }

    public TGM getModel() {
        return this.model;
    }

    public void setModel(TGM model) {
        this.model = model;
    }

    public void collide(Object o1 , Object o2) {
        controler.collide(o1, o2);
    }

    public void addBall(Ball ball) {
        this.model.addBall(ball);
    }

    public void removeBall(Ball ball) {
        this.model.removeBall(ball);
    }


    public TGPCT getControler() {
        return this.controler;
    }

    public void setControler(TGPCT controler) {
        this.controler = controler;
    }


}