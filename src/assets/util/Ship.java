package src.assets.util;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.System.exit;
import static src.GUI.*;
import static src.assets.util.Controls.x;
import static src.assets.util.Controls.y;

public class Ship {
    public static JLabel hit1;
    public static JLabel hit2;
    public static JLabel hit3;
    public static JLabel miss;

    public static boolean isVertical;
    public static boolean boom1;
    public static boolean boom2;
    public static boolean boom3;
    public static int setY;
    public static int setX;
    public static LinkedList<JLabel> missList = new LinkedList<>();
    public static ImageIcon green = DisplayImages.resizeImage("src/assets/images/green.png", 75, 75);
    public static ImageIcon red = DisplayImages.resizeImage("src/assets/images/red.png", 75, 75);


    public static void createShipGrid() {
        // Create ship location
        Random rand = new Random();
        setY = rand.nextInt(6) * 75;
        setX = rand.nextInt(11) * 75;

        // Create and set orientation of ship
        int orient = rand.nextInt(2);
        isVertical = orient == 1;

        // Test: display location and orientation
        System.out.println((setY / 75) + " " + (setX / 75) + " " + isVertical);
    }

    public static void checkOrientation() {
        // Check if hit
        if (isVertical) {
            int one = setY;
            int two = one + 75;
            int three = two + 75;
            if (one == y && setX == x) {
                boom1 = true;
            } else if (two == y && setX == x) {
                boom2 = true;
            } else if (three == y && setX == x) {
                boom3 = true;
            }
        } else {
            int one = setX;
            int two = one + 75;
            int three = two + 75;
            if (one == x && setY == y) {
                boom1 = true;
            } else if (two == x && setY == y) {
                boom2 = true;
            } else if (three == x && setY == y) {
                boom3 = true;
            }
        }
    }

    public static void checkBoom() {
        // Check if ship is sunk
        if (boom1 && boom2 && boom3) {
            boom1 = false;
            boom2 = false;
            boom3 = false;
            JOptionPane.showMessageDialog(null, "Winner!");
            int keepPlaying = JOptionPane.showConfirmDialog(null, "Keep playing?");
            if (keepPlaying == JOptionPane.YES_OPTION) {
                contentPanel.remove(hit1);
                contentPanel.remove(hit2);
                contentPanel.remove(hit3);

                for (JLabel label : missList) {
                    contentPanel.remove(label);
                }
                contentPanel.repaint();

                // Start new game
                synchronized (game) {
                    game.notify();
                }
            } else {
                JOptionPane.showMessageDialog(null, "OK");
                GameThread.stopThread();
                exit(0);
            }
        }
    }
    public static void addMiss() {
        JLabel el = new JLabel(red);
        missList.add(el);
        for (JLabel label : missList) {
            label.setBounds(x, y, 75, 75);
            contentPanel.add(label);
        }
        contentPanel.repaint();
    }

    public static void createShip() {
        contentPanel.revalidate();

        hit1 = new JLabel(green);
        hit2 = new JLabel(green);
        hit3 = new JLabel(green);

        boolean[] isHitAdded = {contentPanel.isAncestorOf(hit1), contentPanel.isAncestorOf(hit2), contentPanel.isAncestorOf(hit3)};

        createShipGrid();
        boom1 = false;
        boom2 = false;
        boom3 = false;
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Controls
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE) {
                    checkOrientation();
                    // Paint
                    if (!isHitAdded[0] && boom1) {
                        hit1.setBounds(x, y, 75, 75);
                        contentPanel.add(hit1);
                        contentPanel.repaint();
                        isHitAdded[0] = true;
                        checkBoom();
                    } else if (!isHitAdded[1] && boom2) {
                        hit2.setBounds(x, y, 75, 75);
                        contentPanel.add(hit2);
                        contentPanel.repaint();
                        isHitAdded[1] = true;
                        checkBoom();
                    } else if (!isHitAdded[2] && boom3) {
                        hit3.setBounds(x, y, 75, 75);
                        contentPanel.add(hit3);
                        contentPanel.repaint();
                        isHitAdded[2] = true;
                        checkBoom();
                    } else {
                        addMiss();
                    }
                }
            }
        });
    }
}