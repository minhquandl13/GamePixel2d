package monster;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_SkeletonLord extends Entity {
    GamePanel gp;
    public static final String monName = "Skeleton Lord";

    public MON_SkeletonLord(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 1;
        defense = 2;
        exp = 50;
        knockBackPower = 5;
        sleep = true;

        int size = gp.tileSize * 5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
        getAttackImage();
        setDialogue();
    }

    public void getImage() {
        int size = 5;

        if (!inRage) {
            up1 = setup("/Monster/skeletonlord_up_1", gp.tileSize * size, gp.tileSize * size);
            up2 = setup("/Monster/skeletonlord_up_2", gp.tileSize * size, gp.tileSize * size);

            down1 = setup("/Monster/skeletonlord_down_1", gp.tileSize * size, gp.tileSize * size);
            down2 = setup("/Monster/skeletonlord_down_2", gp.tileSize * size, gp.tileSize * size);

            left1 = setup("/Monster/skeletonlord_left_1", gp.tileSize * size, gp.tileSize * size);
            left2 = setup("/Monster/skeletonlord_left_2", gp.tileSize * size, gp.tileSize * size);

            right1 = setup("/Monster/skeletonlord_right_1", gp.tileSize * size, gp.tileSize * size);
            right2 = setup("/Monster/skeletonlord_right_2", gp.tileSize * size, gp.tileSize * size);
        }

        if (inRage) {
            up1 = setup("/Monster/skeletonlord_phase2_up_1", gp.tileSize * size, gp.tileSize * size);
            up2 = setup("/Monster/skeletonlord_phase2_up_2", gp.tileSize * size, gp.tileSize * size);

            down1 = setup("/Monster/skeletonlord_phase2_down_1", gp.tileSize * size, gp.tileSize * size);
            down2 = setup("/Monster/skeletonlord_phase2_down_2", gp.tileSize * size, gp.tileSize * size);

            left1 = setup("/Monster/skeletonlord_phase2_left_1", gp.tileSize * size, gp.tileSize * size);
            left2 = setup("/Monster/skeletonlord_phase2_left_2", gp.tileSize * size, gp.tileSize * size);

            right1 = setup("/Monster/skeletonlord_phase2_right_1", gp.tileSize * size, gp.tileSize * size);
            right2 = setup("/Monster/skeletonlord_phase2_right_2", gp.tileSize * size, gp.tileSize * size);
        }
    }

    public void getAttackImage() {
        int size = 5;
        if (!inRage) {
            attackUp1 = setup("/Monster/skeletonlord_attack_up_1", gp.tileSize * size, gp.tileSize * size * 2);
            attackUp2 = setup("/Monster/skeletonlord_attack_up_2", gp.tileSize * size, gp.tileSize * size * 2);

            attackDown1 = setup("/Monster/skeletonlord_attack_down_1", gp.tileSize * size, gp.tileSize * size * 2);
            attackDown2 = setup("/Monster/skeletonlord_attack_down_2", gp.tileSize * size, gp.tileSize * size * 2);

            attackLeft1 = setup("/Monster/skeletonlord_attack_left_1", gp.tileSize * size * 2, gp.tileSize * size);
            attackLeft2 = setup("/Monster/skeletonlord_attack_left_2", gp.tileSize * size * 2, gp.tileSize);

            attackRight1 = setup("/Monster/skeletonlord_attack_right_1", gp.tileSize * size * 2, gp.tileSize * size);
            attackRight2 = setup("/Monster/skeletonlord_attack_right_2", gp.tileSize * size * 2, gp.tileSize * size);
        }

        if (inRage) {
            attackUp1 = setup("/Monster/skeletonlord_phase2_attack_up_1", gp.tileSize * size, gp.tileSize * size * 2);
            attackUp2 = setup("/Monster/skeletonlord_phase2_attack_up_2", gp.tileSize * size, gp.tileSize * size * 2);

            attackDown1 = setup("/Monster/skeletonlord_phase2_attack_down_1", gp.tileSize * size, gp.tileSize * size * 2);
            attackDown2 = setup("/Monster/skeletonlord_phase2_attack_down_2", gp.tileSize * size, gp.tileSize * size * 2);

            attackLeft1 = setup("/Monster/skeletonlord_phase2_attack_left_1", gp.tileSize * size * 2, gp.tileSize * size);
            attackLeft2 = setup("/Monster/skeletonlord_phase2_attack_left_2", gp.tileSize * size * 2, gp.tileSize);

            attackRight1 = setup("/Monster/skeletonlord_phase2_attack_right_1", gp.tileSize * size * 2, gp.tileSize * size);
            attackRight2 = setup("/Monster/skeletonlord_phase2_attack_right_2", gp.tileSize * size * 2, gp.tileSize * size);
        }
    }

    public void setDialogue() {
        dialogues[0][0] = "No one can steal my treasure!";
        dialogues[0][1] = "You will die here";
        dialogues[0][2] = "WELCOME TO YOUR DOOM!";
    }

    public void setAction() {
        if (!inRage && life < maxLife / 2) {
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack *= 2;
        }

        if (getTileDistance(gp.player) < 10) {
            moveTowardPlayer(60);
        } else {
            // Get a random direction
            getRandomDirection(120);
        }
        //check if it attack
        if (!attacking) {
            checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize * 5);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
    }

    public void checkDrop() {
        gp.bossBattleOn = false;
        Progress.skeletonLordDefeated = true;

        // Restore the previous music
        gp.stopMusic();
        gp.playSE(19);

        // Remove the iron doors
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null &&
                    gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
                gp.playSE(21);
                gp.obj[gp.currentMap][i] = null;
            }
        }

        // CAST A DIE
        int i = new Random().nextInt(100) + 1;

        // SET THE MONSTER DROP
        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 75 && i < 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
