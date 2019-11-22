package mrmathami.thegame.audio;

import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import mrmathami.thegame.Main;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.bullet.MachineGunBullet;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.bullet.RocketBullet;

import javax.annotation.Nonnull;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mrmathami.thegame.entity.tile.effect.ExplosionEffect;

import java.io.File;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GameAudio {
    private static AudioClip normalBulletSound = new AudioClip(new File("res/audio/normalbullet.wav").toURI().toString());
    private static AudioClip rocketBulletSound = new AudioClip(new File("res/audio/rocketbullet.wav").toURI().toString());
    private static AudioClip machineBulletSound = new AudioClip(new File("res/audio/machinegunbullet.wav").toURI().toString());
    private static AudioClip explosionSound = new AudioClip(new File("res/audio/explosion.mp3").toURI().toString());
    private static AudioClip gameSound = new AudioClip(new File("res/audio/tavern.mp3").toURI().toString());
    @Nonnull
    private static final Map<Class<? extends GameEntity>, AudioClip> ENTITY_AUDIO_MAP = new HashMap<>(Map.ofEntries(
            Map.entry(NormalBullet.class, normalBulletSound),
            Map.entry(RocketBullet.class, rocketBulletSound),
            Map.entry(MachineGunBullet.class, machineBulletSound),
            Map.entry(ExplosionEffect.class, explosionSound)
    ));


    private GameAudio()
    {
    }

    public static void playSound(Class entityClass)
    {
        ENTITY_AUDIO_MAP.get(entityClass).play();
    }

    public static void playThemeSong()
    {
        gameSound.setVolume(0.6);
        gameSound.setCycleCount(AudioClip.INDEFINITE);
        gameSound.play();
    }
}
