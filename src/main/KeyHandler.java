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
                        gp.ui.titleScreenState = 1;
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
            }
            else if (gp.ui.titleScreenState == 1) {
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
        // PLAY STATE
        if (gp.gameState == gp.playState) {
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
            if (code == KeyEvent.VK_SPACE) {
                spacePressed = true;
            }
        }

        // DEBUG
        if (code == KeyEvent.VK_T) {
            if (!checkDrawTime) {
                checkDrawTime = true;
            } else {
                checkDrawTime = false;
            }
        }
        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
        }
        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
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
    }
}
