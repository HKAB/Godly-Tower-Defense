package mrmathami.thegame.entity.enemy.boss;

import javafx.scene.media.AudioClip;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;
import mrmathami.thegame.audio.GameAudio;
import mrmathami.thegame.entity.tile.cutineffect.BossCutInEffect;

public class MedicBossEnemy extends BossEnemy {
    public MedicBossEnemy (long createdTick, double posX, double posY) {
        super(createdTick, posX, posY, Config.MEDIC_BOSS_ENEMY_WIDTH, Config.MEDIC_BOSS_ENEMY_HEIGHT, Config.MEDIC_BOSS_ENEMY_HEALTH, Config.MEDIC_BOSS_ENEMY_ARMOR, Config.MEDIC_BOSS_ENEMY_SPEED, Config.MEDIC_BOSS_ENEMY_REWARD, Config.MEDIC_BOSS_ENEMY_GID);
    }

    @Override
    public void skillCheck(GameField field) {
        if (!isDestroyed()) setHealth(getHealth() + Config.MEDIC_BOSS_ENEMY_HEALTH_REGENERATE);
        if ((field.getTickCount() - getCreatedTick()) == Config.MEDIC_BOSS_ENEMY_SKILL_ACTIVATE_TIME) {
            //Activate skill
            field.addSFX(new BossCutInEffect(field.getTickCount(), Config.MEDIC_BOSS_ENEMY_CUT_IN_URI));
            GameAudio.getInstance().playSound(new AudioClip(GameAudio.medicSkillSound));
            setGID(Config.MEDIC_BOSS_ENEMY_UBER_GID);
            setImmortal(true);
        }
        if ((field.getTickCount() - getCreatedTick()) == Config.MEDIC_BOSS_ENEMY_SKILL_DEACTIVATE_TIME) {
            //Deactivate skill
            setGID(Config.MEDIC_BOSS_ENEMY_GID);
            setImmortal(false);
        }
    }
}