package main;

import entity.NPC_OldMan;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    public GamePanel gp;
    //region NPC Oldman position const
    private final int OLDMAN_X = 21;
    private final int OLDMAN_Y = 21;
    //endregion

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * OLDMAN_X;
        gp.npc[0].worldY = gp.tileSize * OLDMAN_Y;
    }
}
