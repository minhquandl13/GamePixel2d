package main;

import object.OBJ_Boots;
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


    //region Chest const
    private final int BOOTS_X = 37;
    private final int BOOTS_Y = 42;
    //endregion

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // TODO: quan - range over Exception

    }
}
