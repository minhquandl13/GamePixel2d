package entity;

import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.util.ArrayList;
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

        detectPlate();
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

    public void detectPlate() {
        // Create a plate list
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        for (int i = 0; i < gp.iTile[1].length; i++) {
            if (gp.iTile[gp.currentMap][i] != null &&
                    gp.iTile[gp.currentMap][i].name != null &&
                    gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)) {
                plateList.add(gp.iTile[gp.currentMap][i]);
            }
        }

        // Create a rock list
        ArrayList<Entity> rockList = new ArrayList<>();
        for (int i = 0; i < gp.npc[1].length; i++) {
            if (gp.npc[gp.currentMap][i] != null &&
                    gp.npc[gp.currentMap][i].name.equals(NPC_BigRock.npcName)) {
                rockList.add(gp.npc[gp.currentMap][i]);
            }
        }

        int count = 0;

        // Scan the plate list
        for (InteractiveTile interactiveTile : plateList) {
            int xDistance = Math.abs(worldX - interactiveTile.worldX);
            int yDistance = Math.abs(worldY - interactiveTile.worldY);
            int distance = Math.max(xDistance, yDistance);

            if (distance < 8) {
                if (linkedEntity == null) {
                    linkedEntity = interactiveTile;
                    gp.playSE(3);
                }
            } else {
                if (linkedEntity == interactiveTile) {
                    linkedEntity = null;
                }
            }
        }

        // Scan the rock list
        for (Entity entity : rockList) {
            // Count the rock on the plate
            if (entity.linkedEntity != null) {
                count++;
            }
        }

        // If all the rocks are on the plates, the iron door open
        if (count == rockList.size()) {
            for (int i = 0; i < gp.obj[1].length; i++) {
                if (gp.obj[gp.currentMap][i] != null &&
                        gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
                    gp.obj[gp.currentMap][i] = null;
                    gp.playSE(21);
                }
            }
        }
    }
}
