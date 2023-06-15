package model.object;

import controller.Entity;
import controller.GamePanel;

public class OBJ_Key extends Entity {
    public static final String objName = "Key";

    public OBJ_Key(GamePanel gp) {
        super(gp);

        type = type_consumable;
        name = objName;
        down1 = setup("/Object/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a door.";
        price = 30;
        stackable = true;

        setDialogue();
    }

    public boolean use(Entity entity) {
        int objIndex = getDetected(entity, gp.obj, "Door");

        if (objIndex != 999) {
            startDialogue(this, 0);
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        } else {
            startDialogue(this, 1);
            return false;
        }
    }

    public void setDialogue() {
        dialogues[0][0] = "You use the" + name + " and open the door";

        dialogues[1][0] = "What are you doing?";
    }
}
