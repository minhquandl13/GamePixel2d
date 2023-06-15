package model.interactiveTile;

import controller.Entity;
import controller.GamePanel;
import model.interactiveTile.InteractiveTile;

import java.awt.*;

public class IT_DestructibleWall extends InteractiveTile {
    GamePanel gp;

    public IT_DestructibleWall(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/Tiles_Interactive/destructiblewall", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 1;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.currentWeapon.type == type_pickAxe) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    public void playSE() {
        gp.playSE(20);
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }

    public Color getParticleColor() {
        Color color = new Color(65, 65, 65);
        return color;
    }

    public int getParticleSize() {
        int size = 6; //6 pixels
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
