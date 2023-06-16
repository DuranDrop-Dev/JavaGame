package src.assets.util;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import static src.GUI.*;
import static src.assets.util.Controls.x;
import static src.assets.util.Controls.y;

public class Target {
    public static boolean isVertical;
    public static int setY;
    public static int setX;
    public static ImageIcon green = DisplayImages.resizeImage("src/assets/images/green.png", 75, 75);
    public static ImageIcon red = DisplayImages.resizeImage("src/assets/images/red.png", 75, 75);
    public static boolean[] booms;
    public static int missSize = 16;
    public static boolean[] isMissAdded;
    public static int hitSize = 3;
    public static boolean[] isHitAdded;
    public static JLabel[] missFire;
    public static JLabel[] hitFire;

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

    public static void targetOrientation() {
        // Check if hit
        if (isVertical) {
            int one = setY;
            int two = one + 75;
            int three = two + 75;
            if (one == y && setX == x) {
                booms[0] = true;
            }
            if (two == y && setX == x) {
                booms[1] = true;
            }
            if (three == y && setX == x) {
                booms[2] = true;
            }
        } else {
            int one = setX;
            int two = one + 75;
            int three = two + 75;
            if (one == x && setY == y) {
                booms[0] = true;
            }
            if (two == x && setY == y) {
                booms[1] = true;
            }
            if (three == x && setY == y) {
                booms[2] = true;
            }
        }
    }

    public static void checkBoom() {
        // Check if target is sunk
        int threeToWIn = 0;

        for (boolean boom : booms) {
            if (!boom) {
                threeToWIn += 0;
            } else {
                threeToWIn += 1;
            }
        }

        if (threeToWIn == 3) {
            synchronized (contentPanel) {
                contentPanel.notifyAll();
            }
        }
    }

    public static void removePaint() {
        JOptionPane.showMessageDialog(null, "Winner!");

        for (JLabel l : hitFire) {
            contentPanel.remove(l);
            contentPanel.repaint();
        }

        for (JLabel l : missFire) {
            contentPanel.remove(l);
            contentPanel.repaint();
        }

        missFire = new JLabel[0];
        hitFire = new JLabel[0];
        isHitAdded = new boolean[0];
        isMissAdded = new boolean[0];

        mainPanel.remove(contentPanel);
        mainPanel.revalidate();
        mainPanel.repaint();

        frame.revalidate();
        frame.repaint();
    }

    public static void createTarget() {
        isMissAdded = new boolean[missSize];
        missFire = new JLabel[missSize];

        for (int i = 0; i < missSize; i++) {
            missFire[i] = new JLabel(red);
        }

        for (int i = 0; i < missSize; i++) {
            isMissAdded[i] = contentPanel.isAncestorOf(missFire[i]);
        }

        booms = new boolean[hitSize];
        isHitAdded = new boolean[hitSize];
        hitFire = new JLabel[hitSize];

        for (int i = 0; i < hitSize; i++) {
            hitFire[i] = new JLabel(green);
        }

        for (int i = 0; i < hitSize; i++) {
            isHitAdded[i] = contentPanel.isAncestorOf(hitFire[i]);
        }

        Arrays.fill(booms, false);

        createTargetGrid();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Controls
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE) {
                    // Paint
                    targetOrientation();
                    checkHit();
                }

                System.out.println((setY / 75) + " " + (setX / 75) + " " + isVertical);
                System.out.println("booms: " + Arrays.toString(booms));
                System.out.println("hits: " + Arrays.toString(isHitAdded));
                System.out.println("misses: " + Arrays.toString(isMissAdded));
                System.out.println();
            }
        });
    }

    public static void checkHit() {
        boolean hitIsTrue = false;
        int threeToWin = 0;
        for (int h = 0; h < hitSize; h++) {
            if (!isHitAdded[h]) {
                if (booms[h]) {
                    hitFire[h].setBounds(x, y, 75, 75);
                    contentPanel.add(hitFire[h]);
                    contentPanel.repaint();
                    isHitAdded[h] = true;
                    checkBoom();
                    hitIsTrue = true;
                    threeToWin += 1;
                }
            }
        }

        if (!hitIsTrue && threeToWin < hitSize) {
            checkMiss();
        }
    }

    public static void checkMiss() {
        // TODO fix multiple isMissAdded on new game
        for (int i = 0; i < 16; i++) {
            if (!isMissAdded[i]) {
                isMissAdded[i] = true;
                missFire[i].setBounds(x, y, 75, 75);
                contentPanel.add(missFire[i]);
                contentPanel.repaint();
                break;
            }
        }
    }
}



