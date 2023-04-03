package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    public Entity loot;
    boolean opened = false;

    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;

        type = type_obstacle;
        name = "Chest";
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

    public void interact() {
        gp.gameState = gp.dialogueState;

        if (!opened) {
            gp.playSE(3);
            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a " + loot.name + "!");

            if (!gp.player.canObtainItem(loot)) {
                sb.append("\n...But you cannot catty any more!");
            } else {
                sb.append("\n You obtain the " + loot.name + "!");
                gp.player.inventory.add(loot);
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialog = sb.toString();
        } else {
            gp.ui.currentDialog = "It's empty";
        }
    }
}
