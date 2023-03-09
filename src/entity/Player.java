package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    public KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    private final int COIN_MUSIC = 1;
    private final int POWER_MUSIC = 2;
    private final int UNLOCK_MUSIC = 3;
    private final int FANFARE_MUSIC = 4;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        // Screen player position
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        //Player status
        maxLife = 6;
        life = maxLife;

    }

    public void getPlayerImage() {
        up1 = setup("/Player/Walking sprites/boy_up_1");
        up2 = setup("/Player/Walking sprites/boy_up_2");
        down1 = setup("/Player/Walking sprites/boy_down_1");
        down2 = setup("/Player/Walking sprites/boy_down_2");
        left1 = setup("/Player/Walking sprites/boy_left_1");
        left2 = setup("/Player/Walking sprites/boy_left_2");
        right1 = setup("/Player/Walking sprites/boy_right_1");
        right2 = setup("/Player/Walking sprites/boy_right_2");
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("Player/Attacking sprites/boy_attack_up_1");
        attackUp2 = setup("Player/Attacking sprites/boy_attack_up_2");
        attackDown1 = setup("Player/Attacking sprites/boy_attack_down_1");
        attackDown2 = setup("Player/Attacking sprites/boy_attack_down_2");
        attackLeft1 = setup("Player/Attacking sprites/boy_attack_left_1");
        attackLeft2 = setup("Player/Attacking sprites/boy_attack_left_2");
        attackRight1 = setup("Player/Attacking sprites/boy_attack_right_1");
        attackRight2 = setup("Player/Attacking sprites/boy_attack_right_2");
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed
                || keyH.leftPressed || keyH.rightPressed
                || keyH.spacePressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.getcChecker().checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.getcChecker().checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.getcChecker().checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.getcChecker().checkEntity(this, gp.monster);
            contacMonster(monsterIndex);

            //CHECK EVENT
            gp.eHandler.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyH.spacePressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            gp.keyH.spacePressed = false;

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

        // This needs to be outside of key if statement!
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void pickUpObject(int i) {
        if (i != 999) {

        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            if (gp.keyH.spacePressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contacMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                life -= 1;
                invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

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

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, null);

        // Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // DEBUG
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible" + invincibleCounter, 10, 400);
    }
}





