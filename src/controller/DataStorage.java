package controller;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    // PLAYER STATS
    public int level;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int strength;
    public int dexterity;
    public int exp;
    public int nextLevelExp;
    public int coin;

    // PLAYER INVENTORY
    public ArrayList<String> itemNames = new ArrayList<>();
    public ArrayList<Integer> itemAmounts = new ArrayList<>();
    public int currentWeaponSlot;
    public int currentShieldSlot;

    // OBJECT ON MAP
    public String[][] mapObjectNames;
    public int[][] mapObjectWorldX;
    public int[][] mapObjectWorldY;
    public String[][] mapObjectLootNames;
    public boolean[][] mapObjectOpened;
}
