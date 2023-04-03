package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public GamePanel gp;
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean spacePressed;
    public boolean checkDrawTime = false;
    public boolean shotKeyPressed;
    public boolean enterPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE OR MENU
        if (gp.gameState == gp.titleState) {
            titleState(code);


        }
        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);

        }
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);

        }
        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);

        }
        // CHARACTER STATE
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }

        // OPTIONS STATE
        else if (gp.gameState == gp.optionsState) {
            optionsState(code);
        }

        // GAME OVER STATE
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }

        // TRADE STATE
        else if (gp.gameState == gp.tradeState) {
            tradeState(code);
        }
    }

    public void titleState(int code) {
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_UP) {
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 3;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 3) {
                    gp.ui.commandNumber = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNumber == 0) {
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
//                    gp.ui.titleScreenState = 1;
                }
                if (gp.ui.commandNumber == 1) {
                    // ADD LATER

                }
                if (gp.ui.commandNumber == 2) {
                    // ADD LATER
                }
                if (gp.ui.commandNumber == 3) {
                    System.exit(0);
                }
            }
        } else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_UP) {
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 3;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 3) {
                    gp.ui.commandNumber = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNumber == 0) {
                    System.out.println("Do some fighter specific stuff");
                    gp.gameState = gp.playState;
                    gp.playMusic(gp.music.BACKGROUND_MUSIC);
                }
                if (gp.ui.commandNumber == 1) {
                    System.out.println("Do some thief specific stuff");
                    gp.gameState = gp.playState;
                    gp.playMusic(gp.music.BACKGROUND_MUSIC);
                }
                if (gp.ui.commandNumber == 2) {
                    System.out.println("Do some sorcerer specific stuff");
                    gp.gameState = gp.playState;
                    gp.playMusic(gp.music.BACKGROUND_MUSIC);
                }
                if (gp.ui.commandNumber == 3) {
                    gp.ui.titleScreenState = 0;
                    gp.ui.commandNumber = 0;
                }
            }
        }

    }

    public void playState(int code) {

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        if (code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }

        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }


        // DEBUG
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
        if (code == KeyEvent.VK_R) {
            switch (gp.currentMap) {
                case 0:
                    gp.tileM.loadMap("/Map/worldV3.txt", 0);
                    break;
                case 1:
                    gp.tileM.loadMap("/Map/interior01.txt", 1);
                    break;
            }

        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }

    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }

    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
        playerInventory(code);
    }

    public void optionsState(int code) {

        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0:
                maxCommandNum = 4;
                break;
            case 3:
                maxCommandNum = 1;
                break;
        }

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNumber--;
            gp.playSE(9);
            if (gp.ui.commandNumber < 0) {
                gp.ui.commandNumber = maxCommandNum;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNumber++;
            gp.playSE(9);
            if (gp.ui.commandNumber > maxCommandNum) {
                gp.ui.commandNumber = 0;
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNumber == 0 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if (gp.ui.commandNumber == 1 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(9);
                }
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNumber == 0 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);

                }
                if (gp.ui.commandNumber == 1 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(9);

                }
            }
        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNumber--;
            if (gp.ui.commandNumber < 0) {
                gp.ui.commandNumber = 1;
            }
            gp.playSE(9);
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNumber++;
            if (gp.ui.commandNumber > 1) {
                gp.ui.commandNumber = 0;
            }
            gp.playSE(9);
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNumber == 0) {
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            } else if (gp.ui.commandNumber == 1) {
                gp.gameState = gp.titleState;
//                gp.restart();
            }
        }
    }

    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 2;
                }
                gp.playSE(9);
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 2) {
                    gp.ui.commandNumber = 0;
                }
                gp.playSE(9);
            }
        }
        if (gp.ui.subState == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
        if (gp.ui.subState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);

            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSotCol != 0) {
                gp.ui.playerSotCol--;
                gp.playSE(9);
            }

        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }

        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSotCol != 4) {
                gp.ui.playerSotCol++;
                gp.playSE(9);
            }
        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);

            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }

        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }

        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if (code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
    }
}
