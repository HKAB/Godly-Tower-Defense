package mrmathami.thegame.entity.enemy;

import javafx.scene.media.AudioClip;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;
import mrmathami.thegame.audio.GameAudio;
import mrmathami.thegame.entity.LivingEntity;
import mrmathami.thegame.entity.tile.cutineffect.BossCutInEffect;
import mrmathami.thegame.net.MPGameField;
import mrmathami.thegame.net.MPSocketController;

import javax.annotation.Nonnull;

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

    @Override
    public boolean onEffect(@Nonnull GameField field, @Nonnull LivingEntity livingEntity) {
        field.harmPlayer(field.getHealth());
        field.setMoney(field.getMoney() - this.getReward());
        if (field.isMultiplayer() && !(field instanceof MPGameField)) {
            MPSocketController socket = MPSocketController.getCurrentInstance();
            socket.sendState(field.getHealth());
        }
        setHealth(Long.MIN_VALUE);
        this.reborn = true;
        return false;
    }
}