package controller;

import controller.EventRect;
import controller.GamePanel;
import controller.Progress;
import controller.Entity;

public class EventHandler {
    public GamePanel gp;
    public EventRect[][][] eventRect;
    public Entity eventMaster;

    public int previousEventX;
    public int previousEventY;
    public boolean canTouchEvent = true;
    public int tempMap;
    public int tempCol;
    public int tempRow;


    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventMaster = new Entity(gp);
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
        setDialogue();
    }

    public void setDialogue() {
        eventMaster.dialogues[0][0] = "You fall into a bit";

        eventMaster.dialogues[1][0] = "You drink Water.\nYour life and mana have been recovered.\n" +
                "(The progress has been saved)";

        eventMaster.dialogues[1][1] = "Damn, this is good water";
    }

    public void checkEvent() {
        //Check if the playerChar have away 1 more than title previos event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;

        }
        if (canTouchEvent) {
            if (hit(0, 27, 16, "right")) {
                damagePit(gp.dialogueState);
            } else if (hit(0, 23, 12, "up")) {
                healingPool(gp.dialogueState);
            } else if (hit(0, 10, 39, "any")) { // to the merchant's house
                teleport(1, 12, 13, gp.indoor);
            } else if (hit(1, 12, 13, "any")) { // to outside
                teleport(0, 10, 39, gp.outside);
            } else if (hit(1, 12, 9, "up")) {
                speak(gp.npc[1][0]);
            } else if (hit(0, 12, 9, "any")) {
                teleport(2, 9, 41, gp.dungeon); //to the dungeon
            } else if (hit(2, 9, 41, "any")) {
                teleport(0, 12, 9, gp.outside); //to the outside
            } else if (hit(2, 8, 7, "any")) {
                teleport(3, 26, 41, gp.dungeon); //to B2
            } else if (hit(3, 26, 41, "any")) {
                teleport(2, 8, 7, gp.dungeon); //to B1
            }  else if (hit(3, 25, 27, "any")) {
                skeletonLord(); // BOSS
            }
        }
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.playSE(6);
        eventMaster.startDialogue(eventMaster, 0);
        gp.player.life -= 1;
        //  eventRect[col][row].eventDone= true;
        canTouchEvent = false;
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;
            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;

        }

        return hit;
    }

    public void healingPool(int gameState) {
        if (gp.keyH.spacePressed) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(2);
            eventMaster.startDialogue(eventMaster, 1);
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
            gp.getSaveAndLoad().save();
        }
    }

    public void teleport(int map, int col, int row, int area) {
        gp.gameState = gp.transitionState;
        gp.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;

        canTouchEvent = false;
        gp.playSE(13);
    }

    public void speak(Entity entity) {
        if (gp.keyH.spacePressed) {
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }

    public void skeletonLord() {
        if (!gp.bossBattleOn && !Progress.skeletonLordDefeated) {
            gp.gameState = gp.cutsceneState;
            gp.cutsceneManager.sceneNum = gp.cutsceneManager.skeletonLord;
        }
    }
}
