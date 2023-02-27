package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    public GamePanel gp;
    public Font arial_40;
    public BufferedImage keyImage;
<<<<<<< Updated upstream
    private int messageCounter = 0;
=======
    public boolean messageOn = false;
    public String message = "";
    private final int KEY_POSITION_X = 74;
    private final int KEY_POSITION_Y = 65;

>>>>>>> Stashed changes

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.getHasKey(), KEY_POSITION_X, KEY_POSITION_Y);

<<<<<<< Updated upstream
        messageCounter++;

        if (messageCounter > 120) {
            messageCounter = 0;
=======

        // MESSAGE
        if (messageOn) {

            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
>>>>>>> Stashed changes
        }
    }

}
