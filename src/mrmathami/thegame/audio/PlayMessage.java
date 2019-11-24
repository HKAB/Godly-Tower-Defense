package mrmathami.thegame.audio;

import javafx.scene.media.AudioClip;


public class PlayMessage {
    private AudioClip audioClip;

    private double volume;

    public PlayMessage(AudioClip audioClip, double volume) {
        this.audioClip = audioClip;
        this.volume = volume;
    }

    public AudioClip getAudioClip() {
        return audioClip;
    }

    public void setAudioClip(AudioClip audioClip) {
        this.audioClip = audioClip;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
