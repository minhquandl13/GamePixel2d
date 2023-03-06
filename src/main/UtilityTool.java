package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {
    public BufferedImage scaledImage(BufferedImage original, int width, int heigh) {
        BufferedImage scaledImage = new BufferedImage(width, heigh, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0 , 0, width, heigh, null);
        g2.dispose();

        return scaledImage;
    }
}
