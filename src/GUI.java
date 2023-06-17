package src;

import src.assets.components.GamePanel;
import src.assets.util.Controls;
import src.assets.util.DisplayImages;

import javax.swing.*;
import java.awt.*;

import static java.lang.System.exit;
import static src.assets.util.Controls.updatePosition;
import static src.GameEngine.removePaint;

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
    public static boolean isRunning;
    public static Thread gameLoop;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create main JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // Create and set current position JLabel
        currentPosition.setFont(boldFont);
        currentPosition.setForeground(Color.white);
        currentPosition.setBounds((FRAME_WIDTH / 2) - 90, FRAME_HEIGHT - 90, 180, 40);
        updatePosition();
        contentPanel.add(currentPosition, SwingConstants.CENTER);

        // Create and set target icon
        Controls.Pad(sprite);
        sprite.setIcon(target);
        sprite.setBounds(75, 75, 75, 75);
        contentPanel.add(sprite);

        // Main panel setting
        mainPanel.setPreferredSize(new Dimension(GUI.FRAME_WIDTH, GUI.FRAME_HEIGHT));
        mainPanel.setLayout(new OverlayLayout(mainPanel));
        mainPanel.setBackground(Color.BLACK);

        // Display JFrame
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Play game
        playGame();
    }

    private static void playGame() {
        isRunning = true;
        gameLoop = new Thread(() -> {
            synchronized (contentPanel) {
                try {
                    while (isRunning) {
                        // create main JPanel
                        mainPanel.add(contentPanel);
                        mainPanel.revalidate();
                        mainPanel.repaint();

                        // create game
                        GameEngine.createTarget();

                        // regenerate images
                        contentPanel.revalidate();
                        contentPanel.repaint();

                        // wait until notifyAll() is called
                        contentPanel.wait();

                        // reset arrays and images
                        removePaint();

                        // Keep playing option
                        int keepPlaying = JOptionPane.showConfirmDialog(null, "Play again?");
                        if (keepPlaying == JOptionPane.NO_OPTION) {
                            JOptionPane.showMessageDialog(null, "Goodbye!");
                            exit(0);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        gameLoop.start();
    }
}
