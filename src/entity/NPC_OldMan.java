package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {
    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 2;

        dialogueSet = -1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/NPC/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/NPC/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/NPC/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/NPC/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/NPC/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/NPC/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/NPC/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/NPC/oldman_right_2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Hello, lad.";
        dialogues[0][1] = "So you've come to this island to find the\ntreasure?";
        dialogues[0][2] = "I used to be a great wizard but now...\nI'm a bit too old for taking an adventure.";
        dialogues[0][3] = "This place has 1 final boss so beware of him,\nhis name Quoc.";
        dialogues[0][4] = "Well, good luck on you.";

        dialogues[1][0] = "If you become tired, rest at the water.";
        dialogues[1][1] = "However, the monsters reappear if you reset.\nI don't know why but that's how it's works.";
        dialogues[1][2] = "In any case don't put yourself too hard.";

        dialogues[2][0] = "I wonder how to open that door...";
        dialogues[2][1] = "If you become tired, rest at the water.";
    }

    public void setAction() {
        if (onPath) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75 && i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
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

//        onPath = true;
    }
}
