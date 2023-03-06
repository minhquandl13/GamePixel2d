package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject{
    public GamePanel gp;
    public OBJ_Heart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Object/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/Object/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/Object/heart_blank.png"));
            image=utilityTool.scaledImage(image, gp.tileSize, gp.tileSize);
            image2=utilityTool.scaledImage(image2, gp.tileSize, gp.tileSize);
            image3=utilityTool.scaledImage(image3, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
