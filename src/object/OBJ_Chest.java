package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    public static final String objName = "Chest";

    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = objName;
        image = setup("/Object/chest", gp.tileSize, gp.tileSize);
        image2 = setup("/Object/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;
        price = 25;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setLoot(Entity loot) {
        this.loot = loot;

        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You open the chest and find a " + loot.name + "!\n...But you cannot catty any more!";
        dialogues[1][0] = "You open the chest and find a " + loot.name + "!\n You obtain the " + loot.name + "!";
        dialogues[2][0] = "It's empty";
    }

    public void interact() {


        if (!opened) {
            gp.playSE(3);

            if (!gp.player.canObtainItem(loot)) {
                startDialogue(this, 0);
            } else {
                startDialogue(this, 1);

                gp.player.inventory.add(loot);
                down1 = image2;
                opened = true;
            }
        } else {
            startDialogue(this, 2);
        }
    }
}
