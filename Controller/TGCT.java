package Controller;

import Model.Ball;
import Model.TGM;
import View.TGV;

public class TGCT {
    TGV view;
    TGM model;
    TGPCT controller;

    public TGCT(TGPCT controller) {
        this.controller = controller;
        this.model = new TGM(this);
        this.view = new TGV(this);
    }

    public void addBall(Ball ball) {
        this.model.addBall(ball);
    }

    public void collide(Object o1, Object o2) {
        controller.collide(o1, o2);
    }

    public void play() {
        this.model.addBall();
    }

    public void removeBall(Ball ball) {
        this.model.removeBall(ball);
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

    public TGPCT getController() {
        return this.controller;
    }

    public void setController(TGPCT controler) {
        this.controller = controler;
    }

}