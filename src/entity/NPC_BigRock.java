package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_BigRock extends Entity {
    public static String npcName = "Big Rock";
    public NPC_BigRock(GamePanel gp) {
        super(gp);

        name = npcName;
        direction = "down";
        speed = 4;

        solidArea = new Rectangle();
        solidArea.x = 2;
        solidArea.y = 6;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 44;
        solidArea.height = 40;

        dialogueSet = -1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        up2 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        down1 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        down2 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        left1 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        left2 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        right1 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
        right2 = setup("/NPC/bigrock", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "It's a giant rock.";
    }

    public void setAction() {
    }

    public void update() {
    }

    public void move(String direction) {
        this.direction = direction;

        checkCollision();
        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
    }

    public void speak() {
        facePlayer();
        startDialogue(this, dialogueSet);

        dialogueSet++;
        // TODO: Reset the conversation again
//        if (dialogues[dialogueSet][0] == null) {
//            dialogueSet = 0;
//        }

        // TODO: Just show the last conversation again and again
        if (dialogues[dialogueSet][0] == null) {
            dialogueSet--;
        }

        // TODO: If your player life is low just show the hint how to have more life
//        if (gp.player.life < gp.player.maxLife/3) {
//            dialogueSet = 1;
//        }
    }
}
