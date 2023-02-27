package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    public GamePanel gp;

    //region Key const
    private final int KEY1_X = 23;
    private final int KEY1_Y = 7;
    private final int KEY2_X = 23;
    private final int KEY2_Y = 40;
    private final int KEY3_X = 38;
    private final int KEY3_Y = 8;

    //endregion

    //region Door const
    private final int DOOR1_X = 10;
    private final int DOOR1_Y = 11;
    private final int DOOR2_X = 8;
    private final int DOOR2_Y = 28;
    private final int DOOR3_X = 12;
    private final int DOOR3_Y = 22;

    //endregion

    //region Chest const
    private final int CHEST_X = 10;
    private final int CHEST_Y = 7;
    //endregion

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // TODO: quan - range over Exception

        //region Key object
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = KEY1_X * gp.tileSize;
        gp.obj[0].worldY = KEY1_Y * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = KEY2_X * gp.tileSize;
        gp.obj[1].worldY = KEY2_Y * gp.tileSize;

        gp.obj[2] = new OBJ_Key();
        gp.obj[2].worldX = KEY3_X * gp.tileSize;
        gp.obj[2].worldY = KEY3_Y * gp.tileSize;

        //endregion

        //region Door object
        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = DOOR1_X * gp.tileSize;
        gp.obj[3].worldY = DOOR1_Y * gp.tileSize;

        gp.obj[4] = new OBJ_Door();
        gp.obj[4].worldX = DOOR2_X * gp.tileSize;
        gp.obj[4].worldY = DOOR2_Y * gp.tileSize;

        gp.obj[5] = new OBJ_Door();
        gp.obj[5].worldX = DOOR3_X * gp.tileSize;
        gp.obj[5].worldY = DOOR3_Y * gp.tileSize;

        //endregion

        //region Chest object
        gp.obj[6] = new OBJ_Chest();
        gp.obj[6].worldX = CHEST_X * gp.tileSize;
        gp.obj[6].worldY = CHEST_Y * gp.tileSize;
        //endregion
    }
}
