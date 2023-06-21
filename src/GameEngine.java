package src;

import src.assets.components.GreenMark;
import src.assets.components.RedMark;
import src.assets.components.TopPlayer;
import src.assets.util.HighScore;
import src.assets.util.SoundHandler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static src.GUI.*;
import static src.assets.util.Controls.x;
import static src.assets.util.Controls.y;
import static src.assets.util.HighScore.readDataFromFile;

public class GameEngine {
    public static boolean isVertical; // target orientation
    public static int setY; // target initial Y position
    public static int setX; // target initial X position
    public static int missSize = 12; // incorrect tries allowed
    public static int missedCount; // incorrect tries
    public static int directHit; // correct tries
    public static boolean proceed; // checks if try position x and y are already taken
    public static LinkedList<JLabel> missFire = new LinkedList<>(); // holds red dot JLabels
    public static LinkedList<JLabel> hitFire = new LinkedList<>(); // holds green dot JLabels
    public static ArrayList<String> positionsTaken = new ArrayList<>(); // holds taken x and y positions

    public static void createTargetGrid() {
        // Create target location
        Random rand = new Random();

        setY = rand.nextInt(5) * 75;
        if (setY == 0) setY = 75;

        setX = rand.nextInt(10) * 75;
        if (setX == 0) setX = 75;

        // Create and set orientation of target
        int orient = rand.nextInt(2);
        isVertical = orient == 1;

        // Test: display location and orientation
        System.out.println("Target Position: " + (setY / 75) + "," + (setX / 75) + "\nOrientation Is Vertical: " + isVertical);
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
                playerOne.setWinStreak(1);
                playerInfo.setText(playerOne.displayPlayerAndStats());

                // Stop background music
                SoundHandler.clip.stop();

                // SoundFX
                SoundHandler.RunFX("src/assets/audio/victoryBlip.wav", 0);
                SoundHandler.RunFX("src/assets/audio/victory.wav", 0);

                JOptionPane.showMessageDialog(null, "Winner!");

                // If new win streak is higher record to file
                HighScore.DataModel dataModel = readDataFromFile();
                assert dataModel != null;
                if (playerOne.getWinStreak() > dataModel.winStreak()) {
                    JOptionPane.showMessageDialog(null, "New HighScore!\n" +
                            playerOne.displayPlayerAndStats());
                    HighScore.writeScore(playerOne.getFirstName(), playerOne.getLastName(), playerOne.getWinStreak());
                }

                // continue game loop
                contentPanel.notifyAll();
            }
        }
    }

    public static void removePaint() {
        // Reset components
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

        // Clears arrays
        missFire.clear();
        hitFire.clear();
        positionsTaken.clear();

        // Reset main JPanel
        mainPanel.remove(contentPanel);
        mainPanel.revalidate();
        mainPanel.repaint();

        // Reset main frame
        frame.revalidate();
        frame.repaint();
    }

    public static void createTarget() {
        // Reads and displays top win streak from file
        topPlayer = new TopPlayer();
        highestWinStreak.setText("Top Player: " + topPlayer.displayPlayerAndStats());

        // Check if missFire element has been added to contentPanel
        missedCount = 0;
        directHit = 0;

        // Displays tries left
        int missesLeft = missSize - missedCount;
        missesLeftLabel.setText("Tries Left: " + missesLeft);

        // Create new target grid
        createTargetGrid();
    }

    public static void addGreen() {
        // SoundFX
        SoundHandler.RunFX("src/assets/audio/good.wav", 0);

        // Add position to positionsTaken arraylist
        positionsTaken.add((x + String.valueOf(y)));

        // create new hit image and place on contentPanel
        JLabel hit = new GreenMark();
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
            JLabel miss = new RedMark();
            miss.setBounds(x, y, 75, 75);
            missFire.add(miss);
            contentPanel.add(missFire.get(missedCount - 1));
            contentPanel.repaint();
        }

        // Limited tries are checked here
        if (missedCount == missSize) {
            synchronized (contentPanel) {
                // Reset current win streak
                playerOne.resetWinStreak();
                playerInfo.setText(playerOne.displayPlayerAndStats());

                // BG music is paused
                SoundHandler.clip.stop();

                // Queue lose sound effects
                SoundHandler.RunFX("src/assets/audio/lostBlip.wav", 0);
                SoundHandler.RunFX("src/assets/audio/lost.wav", 0);

                // Restart game
                JOptionPane.showMessageDialog(null, "Out of tries!!! You lose!");
                contentPanel.notifyAll();
            }
        }
    }

    public static void checkSelectedPosition() {
        // Checks if current position is taken
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