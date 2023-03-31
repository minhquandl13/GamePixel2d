package main;

import java.io.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // MUSIC VOLUME
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // SE Volume
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String d = br.readLine();

            // MUSIC Volume
            d = br.readLine();
            gp.music.volumeScale = Integer.parseInt(d);

            // SE Volume
            d = br.readLine();
            gp.se.volumeScale = Integer.parseInt(d);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
