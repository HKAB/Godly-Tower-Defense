package mrmathami.thegame.entity.enemy;

import javafx.scene.media.AudioClip;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;
import mrmathami.thegame.audio.GameAudio;

public class JohnCenaBossEnemy extends BossEnemy {
    public JohnCenaBossEnemy (long createdTick, double posX, double posY) {
        super(createdTick, posX, posY, Config.JOHN_CENA_BOSS_ENEMY_WIDTH / Config.TILE_SIZE, Config.JOHN_CENA_BOSS_ENEMY_HEIGHT / Config.TILE_SIZE, Config.JOHN_CENA_BOSS_ENEMY_HEALTH, Config.JOHN_CENA_BOSS_ENEMY_ARMOR, Config.JOHN_CENA_BOSS_ENEMY_SPEED, Config.JOHN_CENA_BOSS_ENEMY_REWARD, Config.JOHN_CENA_BOSS_ENEMY_GID);
    }

    @Override
    public void skillCheck(GameField field) {
        if ((field.getTickCount() - getCreatedTick()) == Config.JOHN_CENA_BOSS_ENEMY_SKILL_ACTIVATE_TIME) {
            //Activate skill
            GameAudio.getInstance().playSound(new AudioClip(GameAudio.johnCenaSkillSound));
            setGID(Config.JOHN_CENA_BOSS_ENEMY_ON_SKILL_GID);
            setInvisible(true);
        }
        if ((field.getTickCount() - getCreatedTick()) == Config.JOHN_CENA_BOSS_ENEMY_SKILL_DEACTIVATE_TIME) {
            //Deactivate skill
            setGID(Config.JOHN_CENA_BOSS_ENEMY_GID);
            setInvisible(false);
        }
    }
}