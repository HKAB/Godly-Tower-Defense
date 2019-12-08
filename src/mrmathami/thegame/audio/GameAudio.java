package mrmathami.thegame.audio;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mrmathami.thegame.Config;

import java.io.File;

class AudioChannel
{
    private AudioClip audioClip;
    private double volume;

    public AudioChannel(AudioClip audioClip, double volume) {
        this.audioClip = audioClip;
        this.volume = volume;
    }

    public void play() {
        audioClip.setVolume(volume);
        audioClip.play();
    }

    /**
     * Check if this channel is available.
     * @return state of this channel
     */
    public boolean isAvailable() {
        if (audioClip.isPlaying())
            return false;
        return true;
    }

    public AudioClip getAudioClip() {
        return audioClip;
    }

}

public class GameAudio {
    private double mainVolume = Config.INITIAL_MAIN_VOLUME;

    public static String gameSound = new File("res/audio/tavern.mp3").toURI().toString();
    public static String normalBulletSound = new File("res/audio/normalbullet.mp3").toURI().toString();
    public static String rocketBulletSound = new File("res/audio/rocketbullet.mp3").toURI().toString();
    public static String machineBulletSound = new File("res/audio/machinegunbullet.mp3").toURI().toString();
    public static String nyanCatBulletSound = new File("res/audio/nyancatbullet.mp3").toURI().toString();
    public static String stopSignBulletSound = new File("res/audio/signbullet.mp3").toURI().toString();
    public static String explosionSound = new File("res/audio/explosion.mp3").toURI().toString();
    public static String johnCenaSkillSound = new File("res/audio/johncenaskillsound.mp3").toURI().toString();
    public static String binLadenSkillSound = new File("res/audio/binladenskillsound.mp3").toURI().toString();
    public static String sonGokuSkillSound = new File("res/audio/songokuskillsound.mp3").toURI().toString();
    public static String kimJongUnSkillSound = new File("res/audio/kimjongunskillsound.mp3").toURI().toString();
    public static String elonMuskSkillSound = new File("res/audio/elonmuskskillsound.mp3").toURI().toString();
    public static String medicSkillSound = new File("res/audio/medicskillsound.mp3").toURI().toString();

    private AudioClip themeSong = new AudioClip(gameSound);
    private static MediaPlayer mediaPlayer = new MediaPlayer(new Media(gameSound));

    private static final GameAudio INSTANCE = new GameAudio();
    // 10 - the magic number xD
    private static final int MAX_PENDING = Config.AUDIO_MAX_PENDING;
    private int headIndex;
    private int tailIndex;
    // Good explanation of volatile: http://tutorials.jenkov.com/java-concurrency/volatile.html
    private volatile Thread updateThread = null;

    private PlayMessage[] pendingAudio = new PlayMessage[MAX_PENDING];
    private AudioChannel[] audioChannels = new AudioChannel[MAX_PENDING];

    public static GameAudio getInstance()
    {
        return INSTANCE;
    }

    public synchronized void stopService() throws InterruptedException {
        if (updateThread != null)
        {
            updateThread.interrupt();
        }
        updateThread.join();
        updateThread = null;
    }

    public synchronized boolean isServiceRunning()
    {
        return updateThread != null && updateThread.isAlive();
    }

    public void init() {
        if (updateThread == null) {
            updateThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    update();
                }
            });
        }
        startThread();
    }

    private void startThread() {
        if (!updateThread.isAlive()) {
            updateThread.start();
            headIndex = 0;
            tailIndex = 0;
        }
    }

    /**
     * Add audio to queue if possible
     * @param audioClip
     */
    public void playSound(AudioClip audioClip) {
        if ((tailIndex + 1)%MAX_PENDING != headIndex) {
            init();
//            for (int i = headIndex; i != tailIndex; i = (i + 1) % MAX_PENDING) {
//                if (getPendingAudio()[i].getAudioClip().equals(audioClip)) {
//                    getPendingAudio()[i].setVolume(Math.max(volume, getPendingAudio()[i].getVolume()));
//                    return;
//                }
//            }
            getPendingAudio()[tailIndex] = new PlayMessage(audioClip, mainVolume);
            tailIndex = (tailIndex + 1) % MAX_PENDING;
        }
    }

    /**
     * Find an available channel and play
     */
    private void update() {
        // No more request
        if (headIndex == tailIndex) {
            return;
        }
        AudioClip audioClip = getPendingAudio()[headIndex].getAudioClip();
        int channel = -1;
        for (int i = 1; i < MAX_PENDING; i++) {
            if (audioChannels[i] == null || audioChannels[i].isAvailable())
            {
                channel = i;
                break;
            }
        }
        if (channel == -1)
        {
            System.out.println("OUT OF CHANNEL!");
            return;
        }
        audioChannels[channel] = new AudioChannel(audioClip, mainVolume);
        audioChannels[channel].play();
        headIndex = (headIndex + 1) % MAX_PENDING;
    }


    public PlayMessage[] getPendingAudio() {
        return pendingAudio;
    }

    public void playThemeSong() {
        mediaPlayer.setVolume(mainVolume);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void setMainVolume(double mainVolume) {
        mediaPlayer.setVolume(mainVolume);
        this.mainVolume = mainVolume;
    }

    public double getMainVolume()
    {
        return mainVolume;
    }
}
