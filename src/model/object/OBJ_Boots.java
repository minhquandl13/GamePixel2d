package model.object;

import controller.Entity;
import controller.GamePanel;


public class OBJ_Boots extends Entity {
    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gp) {
        super(gp);

        name = objName;
        down1 = setup("/Object/boots", gp.tileSize, gp.tileSize);
        price = 50;
    }
}