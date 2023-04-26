package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
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
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 1;
        defense = 2;
        exp = 10;
        knockBackPower = 5;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 40;
        attackArea.height = 40;
        motion1_duration = 40;
        motion2_duration = 85;

        getImage();
        getAttackImage();
    }

    public void getImage() {
        int sizeOfMonster = 5;

        up1 = setup("/Monster/skeletonlord_up_1", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);
        up2 = setup("/Monster/skeletonlord_up_2", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);

        down1 = setup("/Monster/skeletonlord_down_1", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);
        down2 = setup("/Monster/skeletonlord_down_2", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);

        left1 = setup("/Monster/skeletonlord_left_1", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);
        left2 = setup("/Monster/skeletonlord_left_2", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);

        right1 = setup("/Monster/skeletonlord_right_1", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);
        right2 = setup("/Monster/skeletonlord_right_2", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);
    }

    public void getAttackImage() {
        int sizeOfMonster = 5;

        attackUp1 = setup("/Monster/skeletonlord_attack_up_1", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);
        attackUp2 = setup("/Monster/skeletonlord_attack_up_2", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);

        attackDown1 = setup("/Monster/skeletonlord_attack_down_1", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);
        attackDown2 = setup("/Monster/skeletonlord_attack_down_2", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);

        attackLeft1 = setup("/Monster/skeletonlord_attack_left_1", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);
        attackLeft2 = setup("/Monster/skeletonlord_attack_left_2", gp.tileSize * sizeOfMonster, gp.tileSize);

        attackRight1 = setup("/Monster/skeletonlord_attack_right_1", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);
        attackRight2 = setup("/Monster/skeletonlord_attack_right_2", gp.tileSize * sizeOfMonster, gp.tileSize * sizeOfMonster);
    }

    public void setAction() {
        if (onPath) {
            // Check if it stops chasing
            checkStopChasingOrNot(gp.player, 15, 100);

            // Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        } else {
            // Check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);

            // Get a random direction
            getRandomDirection(120);
        }
        //check if it attack
        if (!attacking) {
            checkAttackOrNot(30, gp.tileSize * 4, gp.tileSize);

        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
//        direction = gp.player.direction;
        onPath = true;
    }

    public void checkDrop() {

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
