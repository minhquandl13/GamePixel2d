package view;

import controller.GamePanel;

import javax.swing.*;
import java.util.Objects;

public class Main {
    public static JFrame window = new JFrame();
    public static void main(String[] args) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Quoc Adventure");
        new Main().setIcon();

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

//        gamePanel.config.loadConfig();

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

    public void setIcon() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().
                getResource("Player/Walking sprites/boy_down_1.png")));
        window.setIconImage(icon.getImage());
    }
}