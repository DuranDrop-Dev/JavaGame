package src.assets.util;

import javax.swing.*;
import java.awt.*;

public class DisplayImages {
    public static ImageIcon resizeImage(String path, int width, int height){
        ImageIcon i = new ImageIcon(path);
        Image image = i.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        return new ImageIcon(resizedImage);
    }
}
