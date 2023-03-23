package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    public Clip clip;
    // TODO: quan - range over Exception
    public URL[] soundURL = new URL[30];
    public final int BACKGROUND_MUSIC = 0;

    public Sound() {
        soundURL[0] = getClass().getResource("/Sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/Sound/coin.wav");
        soundURL[2] = getClass().getResource("/Sound/powerup.wav");
        soundURL[3] = getClass().getResource("/Sound/unlock.wav");
        soundURL[4] = getClass().getResource("/Sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/Sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/Sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/Sound/swingweapon.wav");
        soundURL[8] = getClass().getResource("/Sound/levelup.wav");
        soundURL[9] = getClass().getResource("/Sound/cursor.wav");
        soundURL[10] = getClass().getResource("/Sound/burning.wav");
        soundURL[11] = getClass().getResource("/Sound/cuttree.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {

        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
