package model.object;

import controller.Entity;
import controller.GamePanel;

public class OBJ_Pickaxe extends Entity {
    public static final String objName = "Pickaxe";

    public OBJ_Pickaxe(GamePanel gp) {
        super(gp);

        type = type_pickAxe;
        name = objName;
        down1 = setup("/Object/pickaxe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Pickaxe]\nYou will dig it!";
        price = 75;
        knockBackPower = 10;
        motion1_duration = 20;
        motion2_duration = 40;
    }
}
