package main;

import entity.Entity;

public class CollisionChecker {
    public GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // solidArea.x = 8
        // solidArea.y = 16
        // solidArea.width = 32
        // solidArea.height = 32
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + entity.direction);
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) { // FIXED

            if (gp.obj[gp.currentMap][i] != null) {  // FIXED

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // Get the object's solid area position
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;

                    case "down" -> entity.solidArea.y += entity.speed;

                    case "left" -> entity.solidArea.x -= entity.speed;

                    case "right" -> entity.solidArea.x += entity.speed;

                    default -> throw new IllegalStateException("Unexpected value: " + entity.direction);
                }
                if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {  // FIXED
                    if (gp.obj[gp.currentMap][i].collision) {  // FIXED
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;  // FIXED
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;  // FIXED
            }
        }

        return index;
    }

    // NPC OR MONSTER
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;

        for (int i = 0; i < target[1].length; i++) { // FIXED

            if (target[gp.currentMap][i] != null) { // FIXED

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // Get the object's solid area position
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x; // FIXED
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y; // FIXED

                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;

                    case "down" -> entity.solidArea.y += entity.speed;

                    case "left" -> entity.solidArea.x -= entity.speed;

                    case "right" -> entity.solidArea.x += entity.speed;

                    default -> throw new IllegalStateException("Unexpected value: " + entity.direction);
                }
                if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) { // FIXED
                    if (target[gp.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX; // FIXED
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY; // FIXED
            }
        }

        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // Get the object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;

            case "down" -> entity.solidArea.y += entity.speed;

            case "left" -> entity.solidArea.x -= entity.speed;

            case "right" -> entity.solidArea.x += entity.speed;
            default -> throw new IllegalStateException("Unexpected value: " + entity.direction);
        }
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }
}

