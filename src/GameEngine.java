package src;

import src.assets.util.HighScore;
import src.assets.util.SoundHandler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

import static src.GUI.*;
import static src.assets.util.Controls.x;
import static src.assets.util.Controls.y;
import static src.assets.util.HighScore.readDataFromFile;

public class GameEngine {
    public static boolean isVertical;
    public static int setY;
    public static int setX;
    public static int missSize = 12;
    public static int missedCount;
    public static int directHit;
    public static boolean proceed;
    public static ArrayList<JLabel> missFire = new ArrayList<>();
    public static ArrayList<JLabel> hitFire = new ArrayList<>();
    public static ArrayList<String> positionsTaken = new ArrayList<>();

    public static void createTargetGrid() {
        // Create ship location
        Random rand = new Random();

        setY = rand.nextInt(5) * 75;
        if (setY == 0) setY = 75;

        setX = rand.nextInt(10) * 75;
        if (setX == 0) setX = 75;

        // Create and set orientation of ship
        int orient = rand.nextInt(2);
        isVertical = orient == 1;

        // Test: display location and orientation
        System.out.println((setY / 75) + " " + (setX / 75) + " " + isVertical);
    }

    public static void checkTargetPosition() {
        // Check if hit
        if (isVertical) {
            int one = setY;
            int two = one + 75;
            int three = two + 75;
            if (one == y && setX == x) {
                directHit += 1;
                addGreen();
            } else if (two == y && setX == x) {
                directHit += 1;
                addGreen();

            } else if (three == y && setX == x) {
                directHit += 1;
                addGreen();
            } else {
                addRed();
            }
        } else {
            int one = setX;
            int two = one + 75;
            int three = two + 75;
            if (one == x && setY == y) {
                directHit += 1;
                addGreen();
            } else if (two == x && setY == y) {
                directHit += 1;
                addGreen();
            } else if (three == x && setY == y) {
                directHit += 1;
                addGreen();
            } else {
                addRed();
            }
        }
    }

    public static void checkBoom() {
        // Check if target is sunk
        if (directHit == 3) {
            synchronized (contentPanel) {
                // Update winStreak
                winStreak += 1;
                playerInfo.setText(playerName + "(" + winStreak + ")");

                // Stop background music
                SoundHandler.clip.stop();

                // SoundFX
                SoundHandler.RunFX("src/assets/audio/victoryBlip.wav", 0);
                SoundHandler.RunFX("src/assets/audio/victory.wav", 0);

                JOptionPane.showMessageDialog(null, "Winner!");

                // If new win streak is higher record to file
                HighScore.DataModel dataModel = readDataFromFile();
                assert dataModel != null;
                if (winStreak > dataModel.winStreak()) {
                    JOptionPane.showMessageDialog(null, "New HighScore!\n" +
                            playerName + " : " + winStreak);
                    HighScore.writeScore();
                }

                contentPanel.notifyAll();
            }
        }
    }
    public static void removePaint() {
        for (JLabel l : hitFire) {
            contentPanel.remove(l);
            contentPanel.revalidate();
            contentPanel.repaint();
        }

        for (JLabel l : missFire) {
            contentPanel.remove(l);
            contentPanel.revalidate();
            contentPanel.repaint();
        }

        missFire.clear();
        hitFire.clear();
        positionsTaken.clear();

        mainPanel.remove(contentPanel);
        mainPanel.revalidate();
        mainPanel.repaint();

        frame.revalidate();
        frame.repaint();
    }

    public static void createTarget() {
        // Console position displayed
        System.out.println((setY / 75) + " " + (setX / 75) + " " + isVertical);

        HighScore.DataModel dataModel = readDataFromFile();
        assert dataModel != null;
        highestWinStreak.setText("Highest Score: " + dataModel.name() + "(" + dataModel.winStreak() + ")");

        // Check if missFire element has been added to contentPanel
        missedCount = 0;
        directHit = 0;

        int missesLeft = missSize - missedCount;
        missesLeftLabel.setText("Tries Left: " + missesLeft);

        positionsTaken.clear();
        createTargetGrid();
    }

    public static void addGreen() {
        // SoundFX
        SoundHandler.RunFX("src/assets/audio/good.wav", 0);

        // Add position to positionsTaken arraylist
        positionsTaken.add((x + String.valueOf(y)));

        // create new hit image and place on contentPanel
        JLabel hit = new JLabel(green);
        hit.setBounds(x, y, 75, 75);
        hitFire.add(hit);
        contentPanel.add(hitFire.get(directHit - 1));
        contentPanel.repaint();

        // check for win
        checkBoom();
    }

    public static void addRed() {
        if (missedCount <= missSize) {
            // SoundFX
            SoundHandler.RunFX("src/assets/audio/bad.wav", 0);

            // Add to missCount total
            missedCount += 1;

            // Update tries
            int missesLeft = missSize - missedCount;
            missesLeftLabel.setText("Tries Left: " + missesLeft);

            // Take up position
            positionsTaken.add((x + String.valueOf(y)));

            // Create and add new green dot image
            JLabel miss = new JLabel(red);
            miss.setBounds(x, y, 75, 75);
            missFire.add(miss);
            contentPanel.add(missFire.get(missedCount - 1));
            contentPanel.repaint();
        }

        // Limited tries are checked here
        if (missedCount == missSize) {
            synchronized (contentPanel) {
                winStreak = 0;
                playerInfo.setText(playerName + "(" + winStreak + ")");

                SoundHandler.clip.stop();

                SoundHandler.RunFX("src/assets/audio/lostBlip.wav", 0);
                SoundHandler.RunFX("src/assets/audio/lost.wav", 0);

                JOptionPane.showMessageDialog(null, "Out of tries!!! You lose!");
                contentPanel.notifyAll();
            }
        }
    }

    public static void checkSelectedPosition() {
        proceed = true;
        if (!positionsTaken.isEmpty()) {
            for (String currentPos : positionsTaken) {
                if (currentPos.equals(x + String.valueOf(y))) {
                    proceed = false;
                    break;
                }
            }
        }

        if (proceed) {
            checkTargetPosition();
        }
    }
}