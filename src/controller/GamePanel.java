package controller;

import model.player.Player;
import view.*;
import model.interactiveTile.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    public final int originalTileSize = 16; // 16x16 tile
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 864 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 672 pixels

    // WORLD SETTINGS
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 0;

    // FPS
    public final int FPS = 60;

    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Sound soundEffect = new Sound();
    public Sound music = new Sound();
    public Sound se = new Sound();
    public final CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    public EnvironmentManager eManager = new EnvironmentManager(this);
    public Map map = new Map(this);
    public Thread gameThread;
    public SaveAndLoad saveAndLoad = new SaveAndLoad(this);
    public EntityGenerator entityGenerator = new EntityGenerator(this);
    public CutsceneManager cutsceneManager = new CutsceneManager(this);

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);

    // TODO: quan - range over Exception
    public Entity[][] obj = new Entity[maxMap][20]; // 10 = slot object like items
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public Entity[][] projectile = new Entity[maxMap][20];
    public ArrayList<Entity> entityList = new ArrayList<>();

    public ArrayList<Entity> particleList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    public final int cutsceneState = 11;

    //OTHERS
    public boolean bossBattleOn = false;

    //AREA
    public int nextArea;
    public int currentArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        currentArea = outside;
        eManager.setup();
        gameState = titleState;
    }

    public void resetGame(boolean restart) {
        stopMusic();
        currentArea = outside;
        removeTempEntity();
        bossBattleOn = false;
        player.setDefaultPositions();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();

        if (restart) {
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    // Delta method
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta > 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            // PLAYER
            player.update();
            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            // MONSTER
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].isAlive() && !monster[currentMap][i].isDying()) {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].isAlive()) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive) {
                        projectile[currentMap][i].update();
                    }
                    if (!projectile[currentMap][i].alive) {
                        projectile[currentMap][i] = null;
                    }
                }
            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
            eManager.update();
        }
        if (gameState == pauseState) {
            // NOTHING
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // TILE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        //Map
        else if (gameState == mapState) {
            map.drawFullMapScreen(g2);
        }

        // OTHERS
        else {
            // TILE
            tileM.draw(g2);

            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }
            // ADD ENTITY TO THE LIST
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }

            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }
            //SORT
            entityList.sort(new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //DRAW ENTITY
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            // EMPTY ENTITY LIST
            entityList.clear();

            // ENVIRONMENT
            eManager.draw(g2);

            //MINIMAP
            map.drawMiniMap(g2);

            //CUTSCENE
            cutsceneManager.draw(g2);

            // UI
            ui.draw(g2);
        }

        // DEBUG
        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }
        g2.dispose();
    }


    public CollisionChecker getcChecker() {
        return cChecker;
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public void changeArea() {
        if (nextArea != currentArea) {
            stopMusic();
            if (nextArea == outside) {
                playMusic(0);
            }
            if (nextArea == indoor) {
                playMusic(19);
            }
            if (nextArea == dungeon) {
                playMusic(18);
            }

            aSetter.setNPC();
        }

        currentArea = nextArea;
        aSetter.setMonster();
    }

    public SaveAndLoad getSaveAndLoad() {
        return saveAndLoad;
    }
    public void removeTempEntity() {
        for (int mapnum = 0; mapnum < maxMap; mapnum++) {
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[mapnum][i] != null && obj[mapnum][i].temp) {
                    obj[mapnum][i] = null;
                }
            }
        }
    }
}
