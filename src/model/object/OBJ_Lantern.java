package model.object;

import controller.Entity;
import controller.GamePanel;

public class OBJ_Lantern extends Entity {
    public static final String objName = "Lantern";

    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name = objName;
        down1 = setup("/Object/lantern", gp.tileSize, gp.tileSize);
        description = "[Lantern]\nIlluminates your \n surroundings.";
        price = 20;
        lightRadius = 350;
    }
}