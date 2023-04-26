package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    // PLAYER STATS
    public int level;
    int maxLife;
    int life;
    public int maxMana;
    public int mana;
    public int strength;
    public int dexterity;
    public int exp;
    public int nextLevelExp;
    public int coin;

    // PLAYER INVENTORY
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;

    // OBJECT ON MAP
    public String[][] mapObjectNames;
    public int[][] mapObjectWorldX;
    public int[][] mapObjectWorldY;
    public String[][] mapObjectLootNames;
    public boolean[][] mapObjectOpened;
}
