package src;

import src.assets.util.DisplayImages;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

import static src.GUI.*;
import static src.assets.util.Controls.x;
import static src.assets.util.Controls.y;

public class GameEngine {
    public static boolean isVertical;
    public static int setY;
    public static int setX;
    public static ImageIcon green = DisplayImages.resizeImage("src/assets/images/green.png", 75, 75);
    public static ImageIcon red = DisplayImages.resizeImage("src/assets/images/red.png", 75, 75);
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
                JOptionPane.showMessageDialog(null, "Winner!");
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

        // Check if missFire element has been added to contentPanel
        missedCount = 0;
        directHit = 0;

        positionsTaken.clear();
        createTargetGrid();
    }

    public static void addGreen() {
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
            // Add to missCount total
            missedCount += 1;

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