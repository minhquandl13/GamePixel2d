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
    public boolean guardPressed;
    public boolean godModeOn = false;

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
        else if (gp.gameState == gp.dialogueState
                || gp.gameState == gp.cutsceneState) {
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

        // MAP STATE
        else if (gp.gameState == gp.mapState) {
            mapState(code);
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
                }

                if (gp.ui.commandNumber == 1) {
                    gp.getSaveAndLoad().load();
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
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

    public void mapState(int code) {
        if (code == KeyEvent.VK_M) {
            gp.gameState = gp.playState;
        }
    }

    public void playState(int code) {
        switch (code) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_ESCAPE -> gp.gameState = gp.optionsState;
            case KeyEvent.VK_C -> gp.gameState = gp.characterState;
            case KeyEvent.VK_SPACE -> spacePressed = true;
            case KeyEvent.VK_F -> shotKeyPressed = true;
            case KeyEvent.VK_M -> gp.gameState = gp.mapState;
            case KeyEvent.VK_X -> gp.map.miniMapOn = !gp.map.miniMapOn;
            case KeyEvent.VK_J -> guardPressed = true;

            // DEBUG
            case KeyEvent.VK_T -> checkDrawTime = !checkDrawTime;
            case KeyEvent.VK_R -> {
                switch (gp.currentMap) {
                    case 0 -> gp.tileM.loadMap("/Map (Tile editor version)/worldmap.txt", 0);
                    case 1 -> gp.tileM.loadMap("/Map (Tile editor version)/indoor01.txt", 1);
                    case 2 -> gp.tileM.loadMap("/Map (Tile editor version)/dungeon01.txt", 2);
                    case 3 -> gp.tileM.loadMap("/Map (Tile editor version)/dungeon02.xt", 3);
                }
            }
            case KeyEvent.VK_G -> {
                godModeOn = !godModeOn;
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    public void characterState(int code) {
        switch (code) {
            case KeyEvent.VK_C -> gp.gameState = gp.playState;
            case KeyEvent.VK_ENTER -> gp.player.selectItem();
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

        int maxCommandNum = switch (gp.ui.subState) {
            case 0 -> 4;
            case 3 -> 1;
            default -> 0;
        };

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

    // TODO: quan - Check this example format code and apply it for all code to make code clean
//    public void optionsState2222(int code) {
//
//        int maxCommandNum = switch (gp.ui.subState) {
//            case 0 -> 4;
//            case 3 -> 1;
//            default -> 0;
//        };
//
//        switch (code) {
//            case KeyEvent.VK_ESCAPE -> gp.gameState = gp.playState;
//            case KeyEvent.VK_ENTER -> enterPressed = true;
//            case KeyEvent.VK_W -> {
//                gp.ui.commandNumber--;
//                gp.playSE(9);
//                if (gp.ui.commandNumber < 0) {
//                    gp.ui.commandNumber = maxCommandNum;
//                }
//            }
//            case KeyEvent.VK_S -> {
//                gp.ui.commandNumber++;
//                gp.playSE(9);
//                if (gp.ui.commandNumber > maxCommandNum) {
//                    gp.ui.commandNumber = 0;
//                }
//            }
//            case KeyEvent.VK_A -> {
//                if (gp.ui.subState == 0) {
//                    if (gp.ui.commandNumber == 0 && gp.music.volumeScale > 0) {
//                        gp.music.volumeScale--;
//                        gp.music.checkVolume();
//                        gp.playSE(9);
//                    }
//                    else if (gp.ui.commandNumber == 1 && gp.se.volumeScale > 0) {
//                        gp.se.volumeScale--;
//                        gp.playSE(9);
//                    }
//                }
//            }
//            case KeyEvent.VK_D -> {
//                if (gp.ui.subState == 0) {
//                    if (gp.ui.commandNumber == 0 && gp.music.volumeScale < 5) {
//                        gp.music.volumeScale++;
//                        gp.music.checkVolume();
//                        gp.playSE(9);
//                    }
//                    else if (gp.ui.commandNumber == 1 && gp.se.volumeScale < 5) {
//                        gp.se.volumeScale++;
//                        gp.playSE(9);
//                    }
//                }
//            }
//        }
//    }

    public void gameOverState(int code) {
        switch (code) {
            case KeyEvent.VK_W -> {
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 1;
                }
                gp.playSE(9);
            }
            case KeyEvent.VK_S -> {
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 1) {
                    gp.ui.commandNumber = 0;
                }
                gp.playSE(9);
            }
            case KeyEvent.VK_ENTER -> {
                switch (gp.ui.commandNumber) {
                    case 0 -> {
                        gp.gameState = gp.playState;
                        gp.resetGame(false);
                        gp.playMusic(0);
                    }
                    case 1 -> {
                        gp.gameState = gp.titleState;
                        gp.resetGame(true);
                    }
                }
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
        switch (code) {
            case KeyEvent.VK_W -> {
                if (gp.ui.playerSlotRow != 0) {
                    gp.ui.playerSlotRow--;
                    gp.playSE(9);
                }
            }
            case KeyEvent.VK_A -> {
                if (gp.ui.playerSotCol != 0) {
                    gp.ui.playerSotCol--;
                    gp.playSE(9);
                }
            }
            case KeyEvent.VK_S -> {
                if (gp.ui.playerSlotRow != 3) {
                    gp.ui.playerSlotRow++;
                    gp.playSE(9);
                }
            }
            case KeyEvent.VK_D -> {
                if (gp.ui.playerSotCol != 4) {
                    gp.ui.playerSotCol++;
                    gp.playSE(9);
                }
            }
        }
    }

    public void npcInventory(int code) {
        switch (code) {
            case KeyEvent.VK_W -> {
                if (gp.ui.npcSlotRow != 0) {
                    gp.ui.npcSlotRow--;
                    gp.playSE(9);
                }
            }
            case KeyEvent.VK_A -> {
                if (gp.ui.npcSlotCol != 0) {
                    gp.ui.npcSlotCol--;
                    gp.playSE(9);
                }
            }
            case KeyEvent.VK_S -> {
                if (gp.ui.npcSlotRow != 3) {
                    gp.ui.npcSlotRow++;
                    gp.playSE(9);
                }
            }
            case KeyEvent.VK_D -> {
                if (gp.ui.npcSlotCol != 4) {
                    gp.ui.npcSlotCol++;
                    gp.playSE(9);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_F -> shotKeyPressed = false;
            case KeyEvent.VK_J -> guardPressed = false;
            case KeyEvent.VK_ENTER -> enterPressed = false;
        }
    }
}
