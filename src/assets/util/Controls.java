package src.assets.util;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static src.GUI.*;

public class Controls {
    public static int x = 0;
    public static int y = 0;
    public static int spriteSpeed = 20;
    public static void Pad(JLabel sprite) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Controls
                switch (keyCode) {
                    case KeyEvent.VK_W -> y -= spriteSpeed;
                    case KeyEvent.VK_A -> x -= spriteSpeed;
                    case KeyEvent.VK_S -> y += spriteSpeed;
                    case KeyEvent.VK_D -> x += spriteSpeed;
                }

                // Walls
                if (x < 0) {
                    x=0;
                }
                if (x > FRAME_WIDTH - 164) {
                    x = FRAME_WIDTH - 164;
                }
                if (y < 0) {
                    y=0;
                }
                if (y > FRAME_HEIGHT - 184) {
                    y = FRAME_HEIGHT - 184;
                }

                contentPanel.repaint();
                sprite.setBounds(x, y, ball.getIconWidth(), ball.getIconHeight());
            }
        });
    }

}
