package src.assets.components;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public GamePanel() {
        setLayout(null);
    }
    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Image backgroundImage = new ImageIcon("src/assets/images/grid.jpg").getImage();
        g1.drawImage(backgroundImage, 0, 0, 1050, 675, this);
    }

}

