package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    public KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    private final int COIN_MUSIC = 1;
    private final int POWER_MUSIC = 2;
    private final int UNLOCK_MUSIC = 3;
    private final int FANFARE_MUSIC = 4;
//    int standCounter = 0;

    public boolean attackCanceled = false;


    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;


        // Screen player position
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

//        attackArea.width = 36;
//        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;

//        worldX = gp.tileSize * 12;
//        worldY = gp.tileSize * 13;

        gp.currentMap = 0;
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";
        //Player status
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1; // The more strength he has , the more damage he gives.
        dexterity = 1; // The more dexterity he has,the less  damage he receives.
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
//        currentWeapon = new OBJ_Sword_Normal(gp);
        currentWeapon = new OBJ_Axe(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
//        projectile = new OBJ_Rock(gp);
        attack = getAttack(); // The total attack value is decided by strength and weapon
        defense = getDefense(); // The total defense value is decided by dexterity and shield
    }

    public void setDefaultPositions() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }

    public void restoreLifeAndMan() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }

    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Key(gp));
    }

    private int getAttack() {
        attackArea = currentWeapon.attackArea;

        return attack = strength * currentWeapon.attackValue;
    }

    private int getDefense() {
        return defense = dexterity * currentShield.defenseValue;

    }

    public void getPlayerImage() {
        up1 = setup("/Player/Walking sprites/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/Player/Walking sprites/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/Player/Walking sprites/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Player/Walking sprites/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/Player/Walking sprites/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Player/Walking sprites/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/Player/Walking sprites/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Player/Walking sprites/boy_right_2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        if (currentWeapon.type == type_sword) {
            attackUp1 = setup("/Player/Attacking sprites/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/Player/Attacking sprites/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/Player/Attacking sprites/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/Player/Attacking sprites/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/Player/Attacking sprites/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/Player/Attacking sprites/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/Player/Attacking sprites/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/Player/Attacking sprites/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if (currentWeapon.type == type_axe) {
            attackUp1 = setup("/Player/Attacking sprites/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/Player/Attacking sprites/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/Player/Attacking sprites/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/Player/Attacking sprites/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/Player/Attacking sprites/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/Player/Attacking sprites/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/Player/Attacking sprites/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/Player/Attacking sprites/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }
    }

    public void update() {
        if (attacking) {
            attacking();
        } else if (keyH.upPressed || keyH.downPressed
                || keyH.leftPressed || keyH.rightPressed
                || keyH.spacePressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.getcChecker().checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.getcChecker().checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.getcChecker().checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.getcChecker().checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.getcChecker().checkEntity(this, gp.iTile);

            //CHECK EVENT
            gp.eHandler.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyH.spacePressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            if (keyH.spacePressed && !attackCanceled) {
                //    gp.playSE(7);
                attacking = true;
                spritesCounter = 0;
            }
            attackCanceled = false;
            gp.keyH.spacePressed = false;

            // image change in every 10 frames
            spritesCounter++;
            if (spritesCounter > 12) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }

                spritesCounter = 0;
            }
        }

        if (gp.keyH.shotKeyPressed && !projectile.alive
                && shotAvailableCounter == 30 && projectile.haveResource(this)) {

            //SET DEFAULT COORDINATES, DIRECTION AND USER
            projectile.set(worldX, worldY, direction, true, this);

            // SUBTRACT THE COST (MANA, AMMO ETC.)
            projectile.subtractResource(this);

            // CHECK VACANCY
            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;

            gp.playSE(10);
        }

        // This needs to be outside of key if statement!
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        if (life > maxLife) {
            life = maxLife;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNumber = -1;
            gp.stopMusic();
            gp.playSE(12);
        }
    }

    public void attacking() {
        spritesCounter++;

        if (spritesCounter <= 5) {
            spriteNumber = 1;
        }
        if (spritesCounter > 5 && spritesCounter <= 25) {
            spriteNumber = 2;

            // Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX/Y for the attackArea
            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }

            // attackArea becomes solidArea
            solidAreaWidth = attackArea.width;
            solidAreaHeight = attackArea.height;

            // Check monster collision with the updated worldX, worldY and solidArea
            int monsterIndex = gp.getcChecker().checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);

            int iTileIndex = gp.getcChecker().checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            int projectileIndex = gp.getcChecker().checkEntity(this, gp.projectile);
            damageProjectile(projectileIndex);

            // After checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spritesCounter > 25) {
            spriteNumber = 1;
            spritesCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

            // PICKUP ONLY ITEMS
            if (gp.obj[gp.currentMap][i].type == type_pickupOnly) { // FIXED

                gp.obj[gp.currentMap][i].use(this);        // FIXED
                gp.obj[gp.currentMap][i] = null;                // FIXED
            }
            // OBSTACLE
            else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
            	if(keyH.enterPressed == true) {
            		attackCanceled = true;
            		gp.obj[gp.currentMap][i].interact();
            	}
            }
            // INVENTORY ITEMS
            else {
                String text;
                if (canObtainItem(gp.obj[gp.currentMap][i]) == true) {
                    gp.playSE(1);

                    text = " Got a" + gp.obj[gp.currentMap][i].name + "!";      //FIXED
                } else {
                    text = "You cannot carry any  more !";
                }
                gp.ui.AddMessage(text);
                gp.obj[gp.currentMap][i] = null;            // FIXED DON'T FORGET THIS !!!

            }
        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.spacePressed) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();     //FIXED
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!!invincible && !gp.monster[gp.currentMap][i].dying) {     //FIXED
                gp.playSE(6);

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i, int attack, int knockBackPower) {
        if (i != 999) {
            if (!gp.monster[gp.currentMap][i].invincible) {     //FIXED
                gp.playSE(5);
                int damage = attack - gp.monster[gp.currentMap][i].defense;     //FIXED
                if (damage < 0) {
                    damage = 0;
                }

                knockBack(gp.monster[gp.currentMap][i]);

                gp.monster[gp.currentMap][i].life -= damage;     //FIXED
                gp.ui.AddMessage(damage + "damage!");
                gp.monster[gp.currentMap][i].invincible = true;     //FIXED
                gp.monster[gp.currentMap][i].damageReaction();     //FIXED

                if (gp.monster[gp.currentMap][i].life <= 0) {     //FIXED
                    gp.monster[gp.currentMap][i].dying = true;     //FIXED
                    gp.ui.AddMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");     //FIXED
                    gp.ui.AddMessage("Exp + " + gp.monster[gp.currentMap][i].exp);     //FIXED
                    exp += gp.monster[gp.currentMap][i].exp;     //FIXED
                    checkLevelUp();
                }
            }
        }
    }

    public void knockBack(Entity entity) {
        entity.direction = direction;
        entity.speed += 10;
        entity.knockBack = true;
    }

    public void damageInteractiveTile(int i) {

        if (i != 999 && gp.iTile[gp.currentMap][i].destructible &&     //FIXED
                gp.iTile[gp.currentMap][i].isCorrectItem(this) && !gp.iTile[gp.currentMap][i].invincible) {     //FIXED

            gp.iTile[gp.currentMap][i].playSE();     //FIXED
            gp.iTile[gp.currentMap][i].life--;     //FIXED
            gp.iTile[gp.currentMap][i].invincible = true;     //FIXED

            // GENERATE PARTICLE
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);     //FIXED

            if (gp.iTile[gp.currentMap][i].life == 0) {     //FIXED
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();     //FIXED
            }
        }
    }

    public void damageProjectile(int i) {
        if (i != 999) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDiaglog = "You are level " + level + "now!\n"
                    + "You feel stronger!";
        }
    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSotCol, gp.ui.playerSlotRow);
        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();

            }
            if (selectedItem.type == type_consumable) {
                if(selectedItem.use(this) == true) {
                	if(selectedItem.amount > 1) {
                		selectedItem.amount--;
                	}
                	else {
                		inventory.remove(itemIndex);
                	}
                }
            }
        }
    }

    public int searchItemInInventory(String itemName) {
    	
    	int itemIndex = 999;
    	
    	for (int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break;
			}
		}
    	return itemIndex;
    }
    
    public boolean canObtainItem(Entity item) {
    	
    	boolean canObtain = false;
    	
    	// CHECK IF STACKABLE
    	if(item.stackable == true) {
    		
    		int index = searchItemInInventory(item.name);
    		
    		if(index != 999) {
    			inventory.get(index).amount++;
    			canObtain = true;
    		}
    		else { // New item so need to check vacancy
    			if(inventory.size() != maxIventorySize) {
    				inventory.add(item);
    				canObtain = true;
    			}
    		}
    	}
    	else { // Not STACKABLE so check vacancy
    		if(inventory.size() != maxIventorySize) {
				inventory.add(item);
				canObtain = true;
    		}
    	}
    	return canObtain;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up" -> {
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = up1;
                    }
                    if (spriteNumber == 2) {
                        image = up2;
                    }
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNumber == 1) {
                        image = attackUp1;
                    }
                    if (spriteNumber == 2) {
                        image = attackUp2;
                    }
                }
            }
            case "down" -> {
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = down1;
                    }
                    if (spriteNumber == 2) {
                        image = down2;
                    }
                }
                if (attacking) {
                    if (spriteNumber == 1) {
                        image = attackDown1;
                    }
                    if (spriteNumber == 2) {
                        image = attackDown2;
                    }
                }
            }
            case "left" -> {
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = left1;
                    }
                    if (spriteNumber == 2) {
                        image = left2;
                    }
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNumber == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNumber == 2) {
                        image = attackLeft2;
                    }
                }
            }
            case "right" -> {
                if (!attacking) {
                    if (spriteNumber == 1) {
                        image = right1;
                    }
                    if (spriteNumber == 2) {
                        image = right2;
                    }
                }
                if (attacking) {
                    if (spriteNumber == 1) {
                        image = attackRight1;
                    }
                    if (spriteNumber == 2) {
                        image = attackRight2;
                    }
                }
            }
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}





