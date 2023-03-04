package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject {
    public GamePanel gp;
    public OBJ_Key(GamePanel gp) {
        this.gp = gp;
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Object/key.png"));
            utilityTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
