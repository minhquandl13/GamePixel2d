package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {
	
	GamePanel gp;
	Entity loot;
	boolean opened = false;
	
    public OBJ_Chest(GamePanel gp) {
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
    public void interac() {
    	
    	gp.gameState = gp.dialogueState;
    	
    	if(opened == false) {
    		gp.playSE(3);
    		
    		StringBuilder sb = new StringBuilder();
    		sb.append("You open the chest and find a " + loot.name + "!");
    		
    		if(gp.player.inventory.size() == gp.player.maxIventorySize) {
    			sb.append("\n...But you cannot catty any more!");
    		}
    		else {
    			sb.append("\n You abtain the " + loot.name + "!");
    			gp.player.inventory.add(loot);
    			down1 = image2;
    			opened = true;
    		}
    		gp.ui.currentDiaglog = sb.toString();
    	}
    	else {
    		gp.ui.currentDiaglog = "It's empty";
    	}
    }
}
