package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Entity {

    public OBJ_Door(GamePanel gp) {
       super(gp);
        name = "Door";
       down1 = setup("/Object/door");
       collision = true;

       solidArea.x = 0;
       solidArea.y = 16;
       solidArea.width= 48;
       solidArea.height = 32;
       solidAreaDefaultX = solidArea.x;
       solidAreaDefaultY= solidArea.y;

    }
}
