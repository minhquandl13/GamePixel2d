package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    public GamePanel gp;
    public Font arial_40;
    public BufferedImage keyImage;
    private int messageCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.getHasKey(), 74, 65);

        messageCounter++;

        if (messageCounter > 120) {
            messageCounter = 0;
        }
    }

}
