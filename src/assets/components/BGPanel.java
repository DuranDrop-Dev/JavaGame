package src.assets.components;

import src.GUI;

import javax.swing.*;
import java.awt.*;

public class BGPanel extends JFrame {
    public static void createPanel() {
        // Main window
        GUI.contentPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon("src/assets/images/grid.jpg").getImage();
                g.drawImage(backgroundImage, 0, 0, 1050, 675, this);
            }
        };
    }
}
