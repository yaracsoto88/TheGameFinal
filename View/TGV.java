package View;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import Controller.TGCT;


public class TGV extends JFrame implements ActionListener {
    CP controlPanel;
    JButton addBall;
    VW viewer;
    TGCT controller;
    Thread viewerThread;
    JTextField fps;

    public TGV(TGCT controller) {
        this.controller = controller;
        this.viewer = new VW(controller.getModel().getBalls(),this);
        this.controlPanel = new CP();
    
        this.addBall = this.controlPanel.getPlayPause();
        this.addBall.addActionListener(this);
        this.fps = this.controlPanel.getFps();
        this.configureJFrame();
        this.setVisible(true);

        this.viewerThread = new Thread(this.viewer);
        this.viewerThread.start();
       

    }

    private void configureJFrame() {
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setBackground(Color.blue);
        // this.add(viewer);
        this.addComponentsToPane(this.getContentPane());
    }

    private void addComponentsToPane(Container panel) {
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.gridheight = 0;
        c.gridwidth = 0;

        panel.add(controlPanel, c);
        c.gridy++;
        c.gridx = 0;

        panel.add(viewer, c);
        this.pack();
        // panel.validate();
        // panel.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        switch (str) {
            case "Add Ball":
                controller.play();
                break;
            default:
                System.err.println("Acción NO tratada: " + e);
        }
    }

    public CP getControlPanel() {
        return this.controlPanel;
    }

    public void setControlPanel(CP controlPanel) {
        this.controlPanel = controlPanel;
    }
    public void setFpsText(String text){
        this.fps.setText(text);
    }

}