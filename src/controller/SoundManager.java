package controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class SoundManager {
    private static SoundManager instance;
    private Clip[] clips;
    private URL[] soundURL;
    private FloatControl fc;
    private int volumeScale = 3;
    private float volume;

    private SoundManager() {
        clips = new Clip[22];
        soundURL = new URL[22];
        soundURL[0] = getClass().getResource("/Sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/Sound/coin.wav");
        // ...
        soundURL[21] = getClass().getResource("/Sound/dooropen.wav");
        loadClips();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private void loadClips() {
        try {
            for (int i = 0; i < soundURL.length; i++) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                clips[i] = AudioSystem.getClip();
                clips[i].open(ais);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(int soundIndex) {
        if (soundIndex >= 0 && soundIndex < clips.length) {
            clips[soundIndex].setFramePosition(0);
            clips[soundIndex].start();
        }
    }

    public void loop(int soundIndex) {
        if (soundIndex >= 0 && soundIndex < clips.length) {
            clips[soundIndex].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop(int soundIndex) {
        if (soundIndex >= 0 && soundIndex < clips.length) {
            clips[soundIndex].stop();
        }
    }

    public void setVolumeScale(int volumeScale) {
        this.volumeScale = volumeScale;
        checkVolume();
    }

    private void checkVolume() {
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }
        for (Clip clip : clips) {
            if (clip != null) {
                fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                fc.setValue(volume);
            }
        }
    }
}

