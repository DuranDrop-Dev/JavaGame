package src.assets.util;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static src.GUI.*;

public class Controls {
    public static int x = 0;
    public static int y = 0;
    public static void Pad(JLabel sprite) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                switch (keyCode) {
                    case KeyEvent.VK_W -> y -= 20;
                    case KeyEvent.VK_A -> x -= 20;
                    case KeyEvent.VK_S -> y += 20;
                    case KeyEvent.VK_D -> x += 20;
                }
                contentPanel.repaint();

                sprite.setBounds(x, y, ball.getIconWidth(), ball.getIconHeight());
            }
        });
    }

}
