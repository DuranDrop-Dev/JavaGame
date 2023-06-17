package src;

import src.assets.components.GamePanel;
import src.assets.util.*;

import javax.swing.*;
import java.awt.*;

import static java.lang.System.exit;
import static src.GameEngine.removePaint;
import static src.assets.util.Controls.updatePosition;

public class GUI {
    public static int FRAME_WIDTH = 1062; // Default: 1062 *frame width
    public static int FRAME_HEIGHT = 710; // Default: 710 *frame height
    public static Font boldFont = new Font("arial", Font.BOLD, 25); // large font
    public static Font smallFont = new Font("arial", Font.BOLD, 14); // small font
    public static JFrame frame = new JFrame("Sink-A-Target"); //  main frame
    public static JPanel mainPanel = new JPanel(); // main JPanel holds an Overlay layout
    public static final JPanel contentPanel = new GamePanel(); // JPanel holds all content
    public static ImageIcon target = DisplayImages.resizeImage("src/assets/images/target.png", 75, 75); // target image
    public static ImageIcon green = DisplayImages.resizeImage("src/assets/images/green.png", 75, 75); // green dot image
    public static ImageIcon red = DisplayImages.resizeImage("src/assets/images/red.png", 75, 75); // red dot image
    public static JLabel sprite = new JLabel(); // holds and displays target image
    public static JLabel currentPosition = new JLabel("", SwingConstants.CENTER); // displays current position
    public static JLabel playerInfo = new JLabel("", SwingConstants.CENTER); // displays current player info
    public static JLabel highestWinStreak = new JLabel("", SwingConstants.CENTER); // displays highest win streak
    public static JLabel missesLeftLabel = new JLabel("", SwingConstants.CENTER); // displays incorrect tries left
    public static Thread gameLoop; // holds a looped game
    public static String playerName; // global player variable
    public static int winStreak; // global win streak variable
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Setup main JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // Setup current position JLabel
        currentPosition.setFont(smallFont);
        currentPosition.setForeground(Color.white);
        currentPosition.setBounds(0, FRAME_HEIGHT - 110, FRAME_WIDTH / 3, 75);
        updatePosition();
        contentPanel.add(currentPosition);

        // Setup highestWinStreak JLabel
        highestWinStreak.setFont(smallFont);
        highestWinStreak.setForeground(Color.white);
        highestWinStreak.setBounds((FRAME_WIDTH / 3) * 2, FRAME_HEIGHT - 110, FRAME_WIDTH / 3, 75);
        contentPanel.add(highestWinStreak);

        // Setup missesLeftLabel JLabel
        missesLeftLabel.setFont(boldFont);
        missesLeftLabel.setForeground(Color.white);
        missesLeftLabel.setBounds(FRAME_WIDTH / 3, FRAME_HEIGHT - 110, FRAME_WIDTH / 3, 75);
        contentPanel.add(missesLeftLabel);

        // Create and set target icon
        Controls.Pad(sprite);
        sprite.setIcon(target);
        sprite.setBounds(75, 75, 75, 75);
        contentPanel.add(sprite);

        // Main panel setting
        mainPanel.setPreferredSize(new Dimension(GUI.FRAME_WIDTH, GUI.FRAME_HEIGHT));
        mainPanel.setLayout(new OverlayLayout(mainPanel));
        mainPanel.setBackground(new Color(30,52,70));

        // Display JFrame
        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // addPLayer
        addPlayer();

        // Play game
        playGame();
    }

    public static void addPlayer() {
        boolean goodToGo = false;
        while (!goodToGo) {
            try {
                String input = JOptionPane.showInputDialog(null, "Enter Player Name:");
                if (input == null) {
                    throw new NoNullException();
                } else {
                    playerName = input.trim();
                    if (playerName.length() == 0) {
                        throw new NoNullException();
                    } else {
                        if (playerName.length() > 10) {
                            throw new InputLengthLimitException();
                        } else {
                            goodToGo = true;
                        }
                    }
                }
            } catch (InputLengthLimitException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }  catch (NoNullException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            winStreak = 0;
            playerInfo.setText(playerName + "(" + winStreak + ")");
            playerInfo.setBounds(0, 0, FRAME_WIDTH, 75);
            playerInfo.setFont(boldFont);
            playerInfo.setForeground(Color.white);
            contentPanel.add(playerInfo);
        }
    }

    private static void playGame() {
        gameLoop = new Thread(() -> {
            synchronized (contentPanel) {
                try {
                    while (true) {
                        // Music
                        SoundHandler.RunBG("src/assets/audio/bgBeat.wav", -1);

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
                        if (keepPlaying == JOptionPane.YES_NO_OPTION) {
                            System.out.println("New game loaded!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Goodbye!");
                            exit(0);
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        gameLoop.start();
    }
}
