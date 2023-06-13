package src;

import javax.swing.*;
import java.awt.*;

import src.assets.components.*;
import src.assets.util.Controls;

public class GUI {
    public static JFrame frame = new JFrame("Game");
    public static JPanel mainPanel = new JPanel();
    public static JPanel contentPanel;
    public static ImageIcon ball = new ImageIcon("src/assets/images/ball.png");
    public static int FRAME_WIDTH = 1000;
    public static int FRAME_HEIGHT = 700;
    public static JLabel label = new JLabel();

    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        BackPanel.createPanel();
        mainPanel.setPreferredSize(new Dimension(GUI.FRAME_WIDTH, GUI.FRAME_HEIGHT));
        mainPanel.setLayout(new OverlayLayout(mainPanel));

        Controls.Pad(label);
        label.setIcon(ball);

        contentPanel.add(label);

        mainPanel.add(contentPanel);

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
