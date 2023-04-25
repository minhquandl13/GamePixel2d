package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    public Clip clip;
    // TODO: quan - range over Exception
    public URL[] soundURL = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;
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
        soundURL[12] = getClass().getResource("/Sound/gameover.wav");
        soundURL[13] = getClass().getResource("/Sound/stairs.wav");
        soundURL[14] = getClass().getResource("/Sound/sleep.wav");
        soundURL[15] = getClass().getResource("/Sound/blocked.wav");
        soundURL[16] = getClass().getResource("/Sound/party.wav");
        soundURL[17] = getClass().getResource("/Sound/speak.wav");
        soundURL[18] = getClass().getResource("/Sound/Dungeon.wav");
        soundURL[19] = getClass().getResource("/Sound/Merchant.wav");
    }
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
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

    public void checkVolume() {
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }
        fc.setValue(volume);
    }
}
