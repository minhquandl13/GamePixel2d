package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity {
    GamePanel gp;
    public static final String objName = "Blue Heart";
    public OBJ_BlueHeart(GamePanel gp) {
        super(gp);

        this.gp = gp;
        type = type_pickupOnly;
        name = objName;
        down1 = setup("/Object/blueheart", gp.tileSize, gp.tileSize);

        setDialogues();
    }

    public void setDialogues() {
        dialogues[0][0] = "You pick up a beautiful blue gem.";
        dialogues[0][1] = "You find the Blue Heart, the legendary treasure!";
    }

    public boolean use(Entity entity) {
        gp.gameState = gp.cutsceneState;
        gp.cutsceneManager.sceneNum = gp.cutsceneManager.ending;
        return true;
    }
}
