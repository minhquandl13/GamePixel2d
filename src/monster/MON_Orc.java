package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_Orc extends Entity {
    GamePanel gp;

    public MON_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 6;
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
        up1 = setup("/Monster/orc_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/Monster/orc_up_2", gp.tileSize, gp.tileSize);

        down1 = setup("/Monster/orc_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Monster/orc_down_2", gp.tileSize, gp.tileSize);

        left1 = setup("/Monster/orc_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Monster/orc_left_2", gp.tileSize, gp.tileSize);

        right1 = setup("/Monster/orc_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Monster/orc_right_2", gp.tileSize, gp.tileSize);
    }

    public void getAttackImage() {
        attackUp1 = setup("/Monster/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/Monster/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);

        attackDown1 = setup("/Monster/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/Monster/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);

        attackLeft1 = setup("/Monster/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/Monster/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);

        attackRight1 = setup("/Monster/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/Monster/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
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
            getRandomDirection();
        }
        //check if it attack
        if (attacking == false) {
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
