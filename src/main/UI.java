package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    public GamePanel gp;
    public Font arial_40;
    public Font arial_80B;
    public BufferedImage keyImage;
    private int messageCounter = 0;
    public boolean messageOn = false;
    public boolean gameFinished = false;
    public String message = "";
    public double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private final int KEY_POSITION_X = 74;
    private final int KEY_POSITION_Y = 65;


    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        if (gameFinished) {


            String text;
            int textLength;
            int x;
            int y;
            int quadrupleUpTileSize = 4;
            int tripeUpTileSize = 3;
            int doubleUpTileSize = 2;

            // Treasure message
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);

            text = "You found the treasure";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * tripeUpTileSize);
            g2.drawString(text, x, y);

            // Play time
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);

            text = "Your time is: " + decimalFormat.format(playTime) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * quadrupleUpTileSize);
            g2.drawString(text, x, y);

            // End game message
            g2.setFont(arial_80B);
            g2.setColor(Color.YELLOW);

            text = "Congratulations";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * doubleUpTileSize);
            g2.drawString(text, x, y);

            gp.gameThread = null;
        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.getHasKey(), KEY_POSITION_X, KEY_POSITION_Y);

            // PLAY TIME
            playTime += (double) 1 / 60;
            g2.drawString("Time: " + decimalFormat.format(playTime), gp.tileSize * 11, KEY_POSITION_Y);

            // MESSAGE
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                messageCounter++;

                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
