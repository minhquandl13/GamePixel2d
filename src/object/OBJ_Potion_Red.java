package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;
    public static final String objName = "Red Potion";

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;
        type = type_consumable;
        name = objName;
        value = 5;
        down1 = setup("/Object/potion_red", gp.tileSize, gp.tileSize);
        description = "[Red Potion]\nHeals your life by " + value + ".";
        price = 20;
        stackable = true;

        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You drink the " + name + "!\n"
                + "Your life has been recovered by " + value + ".";
    }

    public boolean use(Entity entity) {
        startDialogue(this, 0);
        entity.life += value;
        gp.playSE(2);
        return true;
    }
}
