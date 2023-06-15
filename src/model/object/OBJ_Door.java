package model.object;

import controller.Entity;
import controller.GamePanel;

public class OBJ_Door extends Entity {
    GamePanel gp;
    public static final String objName = "Door";

    public OBJ_Door(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = objName;
        down1 = setup("/Object/door", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        price = 15;

        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You need a key to open this.";
    }

    public void interact() {
        startDialogue(this, 0);
    }
}
