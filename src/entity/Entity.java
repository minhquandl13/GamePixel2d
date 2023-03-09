package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    public GamePanel gp;
    public BufferedImage up1;
    public BufferedImage up2;
    public BufferedImage down1;
    public BufferedImage down2;
    public BufferedImage left1;
    public BufferedImage left2;
    public BufferedImage right1;
    public BufferedImage right2;
    public BufferedImage attackUp1;
    public BufferedImage attackUp2;
    public BufferedImage attackDown1;
    public BufferedImage attackDown2;
    public BufferedImage attackLeft1;
    public BufferedImage attackLeft2;
    public BufferedImage attackRight1;
    public BufferedImage attackRight2;
    public BufferedImage image;
    public BufferedImage image2;
    public BufferedImage image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collision = false;
    protected String[] dialogues = new String[50];

    // STATE
    public int worldX;
    public int worldY;
    public String direction = "down";
    public int spriteNumber = 1;
    protected int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;

    // COUNTER
    public int spritesCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;

    // CHARACTER ATTRIBUTES
    public int type; // 0 = Player, 1 = NPC, 2 = Monster
    public String name;
    public int maxLife;
    public int life;
    public int speed;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDiaglog = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up" -> {
                direction = "down";
            }
            case "down" -> {
                direction = "up";
            }
            case "left" -> {
                direction = "right";
            }
            case "right" -> {
                direction = "left";
            }
        }
    }

    public void update() {
        setAction();

        collisionOn = false;
        gp.getcChecker().checkTile(this);
        gp.getcChecker().checkObject(this, false);
        gp.getcChecker().checkEntity(this, gp.npc);
        gp.getcChecker().checkEntity(this, gp.monster);
        boolean contactPlayer = gp.getcChecker().checkPlayer(this);

        if (this.type == 2 && contactPlayer) {
            if (!gp.player.invincible) {
                // take damage
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        // image change in every 10 frames
        spritesCounter++;
        if (spritesCounter > 12) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }

            spritesCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            switch (direction) {
                case "up" -> {
                    if (spriteNumber == 1) {
                        image = up1;
                    }
                    if (spriteNumber == 2) {
                        image = up2;
                    }
                }
                case "down" -> {
                    if (spriteNumber == 1) {
                        image = down1;
                    }
                    if (spriteNumber == 2) {
                        image = down2;
                    }
                }
                case "left" -> {
                    if (spriteNumber == 1) {
                        image = left1;
                    }
                    if (spriteNumber == 2) {
                        image = left2;
                    }
                }
                case "right" -> {
                    if (spriteNumber == 1) {
                        image = right1;
                    }
                    if (spriteNumber == 2) {
                        image = right2;
                    }
                }
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = utilityTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}

