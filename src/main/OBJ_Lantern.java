package main;

import entity.Entity;

public class OBJ_Lantern extends Entity {
    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name = "Lantern";
        down1 = setup("/Object/lantern", gp.tileSize, gp.tileSize);
        description = "[Lantern]\nIlluminates your \n surroundings.";
        price = 20;
        lightRadius = 250;
    }
}
