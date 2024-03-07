package View;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import Model.Ball;

public class VW extends Canvas implements Runnable {

    private List<Ball> ballList;
    private Image offscreenImage;
    TGV tgv;

    public VW(List<Ball> ballList, TGV tgv) {
        this.tgv = tgv;
        this.ballList = ballList;
        Dimension d = new Dimension(500, 500);
        this.setPreferredSize(d);

    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        if (offscreenImage == null) {
            offscreenImage = createImage(getWidth(), getHeight());
        }
        Graphics offscreenGraphics = offscreenImage.getGraphics();
        clear((Graphics2D) offscreenGraphics);
        Graphics2D g2d = (Graphics2D) offscreenGraphics;
        for (Ball bola : ballList) {
            bola.paint(g2d);
        }
        g.drawImage(offscreenImage, 0, 0, this);

    }

    public void clear(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
