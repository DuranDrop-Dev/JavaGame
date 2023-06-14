package src;

import src.assets.components.BGPanel;
import src.assets.util.Controls;
import src.assets.util.DisplayImages;
import src.assets.util.GameThread;

import javax.swing.*;
import java.awt.*;

public class GUI {
    public static JFrame frame = new JFrame("Sink-A-Ship");
    public static JPanel mainPanel = new JPanel();
    public static JPanel contentPanel;
    public static ImageIcon target = DisplayImages.resizeImage("src/assets/images/target.png", 75, 75);
    public static int FRAME_WIDTH = 1062; // 1062
    public static int FRAME_HEIGHT = 710; // 710
    public static JLabel sprite = new JLabel();
    public static final Thread game = new GameThread();

    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        BGPanel.createPanel();
        mainPanel.setPreferredSize(new Dimension(GUI.FRAME_WIDTH, GUI.FRAME_HEIGHT));
        mainPanel.setLayout(new OverlayLayout(mainPanel));

        Controls.Pad(sprite);
        sprite.setIcon(target);

        game.start();

        contentPanel.add(sprite);

        mainPanel.add(contentPanel, SwingConstants.CENTER);

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
