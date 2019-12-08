package mrmathami.thegame.entity.enemy.boss;

import javafx.scene.media.AudioClip;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;
import mrmathami.thegame.audio.GameAudio;
import mrmathami.thegame.entity.tile.cutineffect.BossCutInEffect;

public class ElonMuskBossEnemy extends BossEnemy {
    public ElonMuskBossEnemy (long createdTick, double posX, double posY) {
        super(createdTick, posX, posY, Config.ELON_MUSK_BOSS_ENEMY_WIDTH, Config.ELON_MUSK_BOSS_ENEMY_HEIGHT, Config.ELON_MUSK_BOSS_ENEMY_HEALTH, Config.ELON_MUSK_BOSS_ENEMY_ARMOR, Config.ELON_MUSK_BOSS_ENEMY_SPEED, Config.ELON_MUSK_BOSS_ENEMY_REWARD, Config.ELON_MUSK_BOSS_ENEMY_GID);
    }

    @Override
    public void skillCheck(GameField field) {
        if ((field.getTickCount() - getCreatedTick()) == Config.ELON_MUSK_BOSS_ENEMY_SKILL_ACTIVATE_TIME) {
            //Activate skill
            field.addSFX(new BossCutInEffect(field.getTickCount(), Config.ELON_MUSK_BOSS_ENEMY_CUT_IN_URI));
            GameAudio.getInstance().playSound(new AudioClip(GameAudio.elonMuskSkillSound));
            setSpeed(getSpeed() * Config.ELON_MUSK_BOSS_ENEMY_SKILL_MULTIPLY);
        }
        if ((field.getTickCount() - getCreatedTick()) == Config.ELON_MUSK_BOSS_ENEMY_SKILL_DEACTIVATE_TIME) {
            //Deactivate skill
            setSpeed(getSpeed() / Config.ELON_MUSK_BOSS_ENEMY_SKILL_MULTIPLY);
        }
    }
}