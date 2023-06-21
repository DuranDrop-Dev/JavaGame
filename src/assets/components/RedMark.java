package src.assets.components;

import javax.swing.*;
import java.awt.*;

public class RedMark extends JLabel {
    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);

        g1.setColor(Color.RED);
        g1.fillOval(13, 12, 50, 50);
    }
}
