package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveAndLoad {
    public GamePanel gp;

    public SaveAndLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();

            // PLAYER STATS
            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;

            // PLAYER INVENTORY
            for (int i = 0; i < gp.player.inventory.size(); i++) {
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmounts.add(gp.player.inventory.get(i).amount);
            }

            // PLAYER EQUIPMENT
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();

            // OBJECTS ON MAP
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];

            for (int mapNumber = 0; mapNumber < gp.maxMap; mapNumber++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (gp.obj[mapNumber][i] == null){
                        ds.mapObjectNames[mapNumber][i] = "NA";
                    } else {
                        ds.mapObjectNames[mapNumber][i] = gp.obj[mapNumber][i].name;
                        ds.mapObjectWorldX[mapNumber][i] = gp.obj[mapNumber][i].worldX;
                        ds.mapObjectWorldY[mapNumber][i] = gp.obj[mapNumber][i].worldY;

                        if (gp.obj[mapNumber][i].loot != null) {
                            ds.mapObjectNames[mapNumber][i] = gp.obj[mapNumber][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNumber][i] = gp.obj[mapNumber][i].opened;
                    }
                }
            }

            // Write the DataStorage object
            oos.writeObject(ds);
        } catch (Exception e) {
            System.out.println("Save Exception");
        }
    }

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));

            // Read the DataStorage
            DataStorage ds = (DataStorage) ois.readObject();

            // PLAYER STATS
            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;

            // PLAYER INVENTORY
            gp.player.inventory.clear();

            for (int i = 0; i < ds.itemNames.size(); i++) {
                gp.player.inventory.add(gp.entityGenerator.getObject(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
            }

            // PLAYER EQUIPMENT
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getAttackImage();

            // OBJECTS ON MAP
            for (int mapNumber = 0; mapNumber < gp.maxMap; mapNumber++) {
                for (int i = 0; i < gp.obj[1].length; i++) {
                    if (ds.mapObjectNames[mapNumber][i].equals("NA")) {
                        gp.obj[mapNumber][i] = null;
                    } else {
                        gp.obj[mapNumber][i] = gp.entityGenerator.getObject(ds.mapObjectNames[mapNumber][i]);
                        gp.obj[mapNumber][i].worldX = ds.mapObjectWorldX[mapNumber][i];
                        gp.obj[mapNumber][i].worldY = ds.mapObjectWorldY[mapNumber][i];

                        if (ds.mapObjectLootNames[mapNumber][i] != null) {
                            gp.obj[mapNumber][i].loot = gp.entityGenerator.getObject(ds.mapObjectLootNames[mapNumber][i]);
                        }

                        gp.obj[mapNumber][i].opened = ds.mapObjectOpened[mapNumber][i];
                        if (gp.obj[mapNumber][i].opened) {
                            gp.obj[mapNumber][i].down1 = gp.obj[mapNumber][i].image2;
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Load Exception");
        }
    }
}
