package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp) {
        super(gp);
        
        type = type_consumable;
        name = "Key";
        down1= setup("/Object/key", gp.tileSize, gp.tileSize);
        description  ="[" + name  + "]\nIt opens a door.";
        price = 30;
        stackable = true;
    }
    public boolean use(Entity entity) {
    	
    	gp.gameState = gp.dialogueState;
    	
    	int objIndex = getDetected(entity, gp.obj, "Door");
    	
    	if(objIndex != 999) {
    		gp.ui.currentDiaglog = "You use the" + name + " and open the door";
    		gp.playSE(3);
    		gp.obj[gp.currentMap][objIndex] = null;
    		return true;
    	}
    	else {
    		gp.ui.currentDiaglog = "What are you doing?";
    		return false;
    	}
    }
}
