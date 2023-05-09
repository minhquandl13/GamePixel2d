package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Fireball extends Projectile {
    GamePanel gp;
    public static final String objName = "Fireball";

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 1;
        knockBackPower = 5;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/Projectile/fireball_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/Projectile/fireball_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/Projectile/fireball_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Projectile/fireball_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/Projectile/fireball_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Projectile/fireball_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/Projectile/fireball_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Projectile/fireball_right_2", gp.tileSize, gp.tileSize);
    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.mana >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    // TODO: part find color
    public Color getParticleColor() {
        Color color = new Color(240, 50, 0);
        return color;
    }

    public int getParticleSize() {
        int size = 10; //6 pixels
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }
}
