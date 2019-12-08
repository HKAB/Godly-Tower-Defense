package mrmathami.thegame.entity.enemy.boss;

import javafx.scene.media.AudioClip;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;
import mrmathami.thegame.audio.GameAudio;
import mrmathami.thegame.entity.tile.cutineffect.BossCutInEffect;

public class SonGokuBossEnemy extends BossEnemy {
    private boolean reborn;

    public SonGokuBossEnemy (long createdTick, double posX, double posY, boolean reborn) {
        super(createdTick, posX, posY, Config.SON_GOKU_BOSS_ENEMY_WIDTH, Config.SON_GOKU_BOSS_ENEMY_HEIGHT, Config.SON_GOKU_BOSS_ENEMY_HEALTH * (long)(reborn ? 1.5 : 1), Config.SON_GOKU_BOSS_ENEMY_ARMOR * (long)(reborn ? 1.5 : 1), Config.SON_GOKU_BOSS_ENEMY_SPEED, Config.SON_GOKU_BOSS_ENEMY_REWARD, Config.SON_GOKU_BOSS_ENEMY_GID + (reborn ? 1 : 0));
        this.reborn = reborn;
    }

    @Override
    public void skillCheck(GameField field) {
        if (this.isDestroyed() && (!reborn)) {
            field.addSFX(new BossCutInEffect(field.getTickCount(), Config.SON_GOKU_BOSS_ENEMY_CUT_IN_URI));
            GameAudio.getInstance().playSound(new AudioClip(GameAudio.sonGokuSkillSound));
            field.doSpawn(new SonGokuBossEnemy(field.getTickCount(), this.getPosX(), this.getPosY(), true));
        }
    }
}