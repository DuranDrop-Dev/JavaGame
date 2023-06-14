package src.assets.util;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
public class ScreenSize extends JFrame {
    public static int frameHeight;
    public static int frameWidth;
    public ScreenSize() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setFrameWidth(getWidth());
                setFrameHeight(getHeight());
                System.out.println("Resized width: " + getFrameWidth());
                System.out.println("Resized height: " + getFrameHeight());
            }
        });
    }
    public static void setFrameHeight(int height) {
        frameHeight = height;
    }
    public static int getFrameHeight() {
        return frameHeight;
    }
    public static void setFrameWidth(int width) {
        frameWidth = width;
    }
    public static int getFrameWidth() {
        return frameWidth;
    }

}
