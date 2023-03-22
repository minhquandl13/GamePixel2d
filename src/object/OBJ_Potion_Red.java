package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;
    int value = 5;

    public OBJ_Potion_Red(GamePanel gp){
        super(gp);

        this.gp = gp;
        type = type_consumable;
        name = "Red Potion";
        down1 = setup("/Object/potion_red",gp.tileSize,gp.tileSize);
        description  = "[ Red Potion ]\nHeals your life by " + value + ".";


    }

    public  void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDiaglog = "You drink the " + name + "!\n"
                + "Your life has been recovered by " + value + ".";
        entity.life += value;
        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        gp.playSE(2);
    }



}
