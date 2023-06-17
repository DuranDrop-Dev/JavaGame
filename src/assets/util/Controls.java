package src.assets.util;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static src.GUI.*;
import static src.GameEngine.*;

public class Controls extends JFrame {
    public static int x = 75;
    public static int y = 75;
    public static int spriteSpeed = 75;
    public static void updatePosition() {
        currentPosition.setText("Position: " + (y / 75) + "," + (x / 75));
    }
    public static void Pad(JLabel sprite) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Controls
                switch (keyCode) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                        if (y > 75) {
                            y -= spriteSpeed;
                            updatePosition();
                        }
                    }
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                        if (x > 75) {
                            x -= spriteSpeed;
                            updatePosition();
                        }
                    }
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                        if (y < 525) {
                            y += spriteSpeed;
                            updatePosition();
                        }
                    }
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                        if (x < 900) {
                            x += spriteSpeed;
                            updatePosition();
                        }
                    }
                    case KeyEvent.VK_SPACE ->
                        // Verify and check position
                        checkSelectedPosition();

                        /*  TEST MESSAGES UNCOMMENT TO VIEW
                            System.out.println((setY / 75) + " " + (setX / 75) + " " + isVertical);
                            System.out.println("positionsTaken: " + positionsTaken.toString());
                            System.out.println("hits: " + directHit);
                            System.out.println("misses: " + missedCount);
                            System.out.println();
                        */
                }

                // Walls
                if (x < 0) {
                    x = 0;
                }
                if (x > frame.getWidth() - 75) {
                    x = frame.getWidth() - 75;
                }
                if (y < 0) {
                    y = 0;
                }
                if (y > frame.getHeight() - 75) {
                    y = frame.getHeight() - 75;
                }

                sprite.setBounds(x, y, target.getIconWidth(), target.getIconHeight());
            }
        });
    }
}
