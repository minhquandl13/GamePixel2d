package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
	GamePanel gp;
	
    public OBJ_Door(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_obstacle;
        name = "Door";
        down1 = setup("/Object/door", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        price = 15;
    }
    public void interact() {
    	gp.gameState = gp.dialogueState;
    	gp.ui.currentDialog = "You need a key to open this.";
    	
    }
}
