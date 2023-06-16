package src;

import src.assets.util.Controls;
import src.assets.util.DisplayImages;
import src.assets.components.GamePanel;
import src.assets.util.Target;

import javax.swing.*;
import java.awt.*;

import static java.lang.System.exit;
import static src.assets.util.Target.removePaint;

public class GUI {
    public static JFrame frame = new JFrame("Sink-A-Ship");
    public static JPanel mainPanel = new JPanel();
    public static final JPanel contentPanel = new GamePanel();
    public static ImageIcon target = DisplayImages.resizeImage("src/assets/images/target.png", 75, 75);
    public static int FRAME_WIDTH = 1062; // 1062
    public static int FRAME_HEIGHT = 710; // 710
    public static JLabel sprite = new JLabel();
    public static JLabel currentPosition = new JLabel();
    public static Font boldFont = new Font("arial", Font.BOLD, 25);
    public static boolean isRunning = true;
    public static Thread g;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        playGame();

        Controls.Pad(sprite);
        sprite.setIcon(target);

        currentPosition.setFont(boldFont);
        currentPosition.setForeground(Color.white);
        currentPosition.setBounds(20, 20, 100, 40);

        mainPanel.setPreferredSize(new Dimension(GUI.FRAME_WIDTH, GUI.FRAME_HEIGHT));
        mainPanel.setLayout(new OverlayLayout(mainPanel));
        mainPanel.setBackground(Color.BLACK);

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void playGame() {
        isRunning = true;
        g = new Thread(() -> {
            synchronized (contentPanel) {
                while (isRunning) {
                    mainPanel.add(contentPanel);
                    mainPanel.repaint();
                    mainPanel.revalidate();
                    System.out.println("contentPanel added");

                    contentPanel.add(sprite);
                    contentPanel.add(currentPosition);

                    Target.createTarget();

                    try {
                        synchronized (contentPanel) {
                            contentPanel.wait();
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    removePaint();

                    int keepPlaying = JOptionPane.showConfirmDialog(null, "Play again?");
                    if (keepPlaying == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(null, "OK");
                        exit(0);
                    }
                }
            }
        });
        g.start();
    }
}
