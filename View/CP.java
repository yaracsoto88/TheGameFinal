package View;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;

public class CP extends JPanel {
    JButton addBall;

    public CP() {
        this.addBall = new JButton("Add Ball");
        this.setLayout(new GridBagLayout());
        addComponentsToPane();

    }

    private void addComponentsToPane() {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 30, 0, 4);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 1;
        c.gridwidth = 1;

        this.add(this.addBall, c);
        c.gridx = 1;
    }

    public JButton getPlayPause() {
        return this.addBall;
    }

    public void setPlayPause(JButton playPause) {
        this.addBall = playPause;
    }

}
